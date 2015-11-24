package be.ipl.groupe17.jeudepiste;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JeuFragment extends Fragment implements LocationListener {
    protected Activity activity;
    private LocationManager locationManager;
    private boolean partieEnCours;
    private Location lastKnownLocation; //TODO -> dans le model

    public JeuFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        partieEnCours = false;
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            //on prend la première location
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            TextView textView = (TextView) activity.findViewById(R.id.text_location);
            textView.setText(lastKnownLocation.toString());
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

    @Override
    public void onLocationChanged(Location location) {
        if (Geolocation.isBetterLocation(location, lastKnownLocation)) {
            lastKnownLocation = location;
            TextView textView = (TextView) activity.findViewById(R.id.text_location);
            if (textView != null) {
                textView.setText(location.toString());
            }
        } else {
            Log.v(getTag(), "New location " + location.toString()
                    + " was less accurate than previous location and has been discarded.");
        }
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
}
