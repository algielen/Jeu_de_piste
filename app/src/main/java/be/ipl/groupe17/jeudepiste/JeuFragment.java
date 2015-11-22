package be.ipl.groupe17.jeudepiste;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class JeuFragment extends Fragment implements LocationListener {
    private LocationManager locationManager;
    private boolean partieEnCours;

    public JeuFragment() {
        // Required empty public constructor
    }


    // TODO : savedInstanceState
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        partieEnCours = false;
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

    private void showErreurPermission() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.insufficient_permission_title)
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            partieEnCours = true;
            // on change le texte du bouton pour refléter l'état actuel
            ((Button) getActivity().findViewById(R.id.button_game)).setText(R.string.button_game_stop);
        }

    }

    public void arreterJeu() {
        //on vérifie qu'on a la permission d'accéder à la géoloc.
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            showErreurPermission();
        } else {
            // TODO : une erreur de permission nous empêcherait d'arrêter? Bof :D
            locationManager.removeUpdates(this);
            partieEnCours = false;
            // on change le texte du bouton pour refléter l'état actuel
            ((Button) getActivity().findViewById(R.id.button_game)).setText(R.string.button_game_start);
        }
    }

    /*
     Méthodes de géolocalisation
     */

    @Override
    public void onLocationChanged(Location location) {
        //TODO
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
