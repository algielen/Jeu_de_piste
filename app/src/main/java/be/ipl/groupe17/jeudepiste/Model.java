package be.ipl.groupe17.jeudepiste;

import android.location.Location;

import java.io.File;
import java.util.ArrayList;

/**
 * TODO : Probablement temporaire le temps qu'on fasse une meilleure implémentation
 */
public class Model {
    private Location currentBestLocation;
    private ArrayList<Zone> zones;
    private File currentPhoto;
    private Zone currentZone; //quand on est occupé à remplir un challenge

    private static Model instance;
    public static Model getInstance(){
        if (instance == null){
            instance = new Model();
        }

        return instance;
    }

    private Model() {
        currentBestLocation = null;
        zones = new ArrayList<>();
        zones.add(new Zone(50.836806d, 4.427361d, 100, "WSP100"));
        zones.add(new Zone(50.836806d, 4.427361d, 200, "WSP200"));
        zones.add(new Zone(50.836806d, 4.427361d, 300, "WSP300"));
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public void setZones(ArrayList<Zone> zones) {
        this.zones = zones;
    }

    public Location getCurrentBestLocation() {
        return currentBestLocation;
    }

    public void setCurrentBestLocation(Location currentBestLocation) {
        this.currentBestLocation = currentBestLocation;
    }

    public File getCurrentPhoto() {
        return currentPhoto;
    }

    public void setCurrentPhoto(File currentPhoto) {
        this.currentPhoto = currentPhoto;
    }

    public Zone getCurrentZone() {
        return currentZone;
    }

    public void setCurrentZone(Zone currentZone) {
        this.currentZone = currentZone;
    }
}

