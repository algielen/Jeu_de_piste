package be.ipl.groupe17.jeudepiste;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class JeuFragment extends Fragment implements LocationListener {
    protected Activity activity;
    private LocationManager locationManager;
    private boolean partieEnCours;
    private Model model;
    private boolean epreuveEnCours = false; // TODO : il faudrait faire un mutex plutot

    public static final int RESULT_OK = -1;

    public JeuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        partieEnCours = false;
        model = Model.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View thisView = inflater.inflate(R.layout.fragment_jeu, container, false);
        Button button = (Button) thisView.findViewById(R.id.button_game);
        // impossible de continuer si ça rate
        if (button == null) {
            Log.e(getTag(), "R.id.button_game n'as pas pu être trouvé.");
            getActivity().finish();
        } else {
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              toggleJeu();
                                          }
                                      }
            );
        }
        return thisView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) activity.findViewById(R.id.button_game);
        if (savedInstanceState != null) {
            partieEnCours = savedInstanceState.getBoolean("partieEnCours");
            if (partieEnCours) {
                button.setText(R.string.button_game_stop);
                demarrerJeu();
            } else {
                button.setText(R.string.button_game_start);
            }
        }
        if (model.getCurrentBestLocation() != null) {
            TextView textView = (TextView) activity.findViewById(R.id.text_location);
            if (textView != null) {
                textView.setText(model.getCurrentBestLocation().toString());
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("partieEnCours", partieEnCours);
        //savedInstanceState.putInt("lastknownlocation", lastknownlocation); //TODO

    }

    private void showErreurPermission() {
        new AlertDialog.Builder(activity).setTitle(R.string.insufficient_permission_title)
                .setMessage(R.string.insufficient_permission)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void toggleJeu() {
        if (partieEnCours) {
            arreterJeu();
        } else {
            demarrerJeu();
        }
    }

    public void demarrerJeu() {
        //on vérifie qu'on a la permission d'accéder à la géoloc.
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            showErreurPermission();
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
            //TODO : fusionner
            if (model.getCurrentBestLocation() == null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //location est null si le provider est disabled
                if (location != null) {
                    model.setCurrentBestLocation(location);
                } else {
                    locationManager.removeUpdates(this);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                TextView textView = (TextView) activity.findViewById(R.id.text_location);
                if (textView != null) {
                    textView.setText(location.toString());
                }
            }

            partieEnCours = true;
            // on change le texte du bouton pour refléter l'état actuel
            ((Button) activity.findViewById(R.id.button_game)).setText(R.string.button_game_stop);
        }

    }

    public void arreterJeu() {
        //on vérifie qu'on a la permission d'accéder à la géoloc.
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            showErreurPermission();
        } else {
            // TODO : une erreur de permission nous empêcherait d'arrêter? Bof :D
            locationManager.removeUpdates(this);
            partieEnCours = false;
            // on change le texte du bouton pour refléter l'état actuel
            ((Button) activity.findViewById(R.id.button_game)).setText(R.string.button_game_start);
        }
    }

    // source : http://stackoverflow.com/a/32088447
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    /*
     Méthodes de géolocalisation
     */


    //TODO : arrêter de recevoir des positions pendant l'épreuve?
    @Override
    public void onLocationChanged(Location location) {
        if (!epreuveEnCours) {
            Location currentBestLocation = model.getCurrentBestLocation();
            if (currentBestLocation == null || Geolocation.isBetterLocation(location, currentBestLocation)) {
                model.setCurrentBestLocation(location);
                //on affiche la nouvelle position TODO : temporaire
                TextView textView = (TextView) activity.findViewById(R.id.text_location);
                if (textView != null) {
                    textView.setText(location.toString());
                }

                //TODO : remplacer ça par un appel à la page HTML etc
                Epreuve currentEpreuve = model.getCurrentEpreuve();
                Zone currentZone = currentEpreuve.getZone();
                if (currentZone.contains(location)) {
                    offerEpreuve(currentEpreuve, currentZone.distanceTo(location));
                }

            } else {
                Log.v(getTag(), "New location " + location.toString()
                        + " was less accurate than previous location and has been discarded.");
            }
        }
    }

    private void offerEpreuve(Epreuve epreuve, double distance) {
        epreuveEnCours = true;
        Zone currentZone = epreuve.getZone();
        // à vocation de test
        new AlertDialog.Builder(activity).setTitle("La position est contenue dans la zone")
                .setMessage("Vous êtes à " + distance + "m de " + currentZone.getNom()
                        + ". Voulez vous tenter l'épreuve ?")
                .setIcon(android.R.drawable.ic_dialog_map)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //on lance l'activity de l'épreuve
                        Intent intent = new Intent(activity, PhotoEpreuveActivity.class);
                        activity.startActivityForResult(intent, PhotoEpreuveActivity.REQUEST_PHOTO_EPREUVE);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        epreuveEnCours = false;
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }


    //retour de la prise de photo
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String title;
        String text;
        if (resultCode == RESULT_OK) {
            title = getResources().getString(R.string.text_success_title);
            text = getResources().getString(R.string.text_success);
        } else {
            title = getResources().getString(R.string.text_failure_title);
            text = getResources().getString(R.string.text_failure);
        }
        switch (requestCode) {
            case PhotoEpreuveActivity.REQUEST_PHOTO_EPREUVE:
                epreuveEnCours = false;
                new AlertDialog.Builder(activity).setTitle(title)
                        .setMessage(text)
                        .setIcon(android.R.drawable.ic_menu_camera)
                        .setPositiveButton("Ok", null)
                        .show();
                break;

            default:
                epreuveEnCours = false;
                //TODO : erreur?
        }
    }
}

