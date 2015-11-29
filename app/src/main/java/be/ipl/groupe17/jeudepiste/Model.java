package be.ipl.groupe17.jeudepiste;

import android.location.Location;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO : Probablement temporaire le temps qu'on fasse une meilleure implémentation
 */
public class Model {
    private Location currentBestLocation;
    private ArrayList<Zone> zones;
    private File currentPhoto;
    private Zone currentZone; //quand on est occupé à remplir un challenge
    private ArrayList<Epreuve> epreuves;
    private int currentEpreuvePosition; //TODO : simplifier

    private static Model instance;

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }

        return instance;
    }

    private Model() {
        currentBestLocation = null;
        //ce sont des tests!

        zones = new ArrayList<>();
        zones.add(new Zone(50.836806d, 4.427361d, 100, "WSP100"));
        zones.add(new Zone(50.836806d, 4.427361d, 200, "WSP200"));
        zones.add(new Zone(50.836806d, 4.427361d, 300, "WSP300"));
        zones.add(new Zone(50.849425d, 4.450960d, 100, "IPL100"));
        zones.add(new Zone(50.849425d, 4.450960d, 200, "IPL200"));

        epreuves = new ArrayList<>();
        epreuves.add(new Epreuve("Maison communale", true, zones.get(0), "picture"));
        epreuves.add(new Epreuve("Arrêt Collecto", true, zones.get(1), "picture"));
        epreuves.add(new Epreuve("1.3", false, zones.get(2), "qcm"));
        epreuves.add(new Epreuve("Parc", false, zones.get(3), "picture"));
        epreuves.add(new Epreuve("Librairie", false, zones.get(4), "picture"));

        currentEpreuvePosition = 2;

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

    public List<Epreuve> getEpreuves() {
        return epreuves;
    }

    public void setEpreuves(ArrayList<Epreuve> epreuves) {
        this.epreuves = epreuves;
    }

    public Epreuve getCurrentEpreuve() {
        return epreuves.get(currentEpreuvePosition);
    }

    public void setCurrentEpreuvePosition(int currentEpreuvePosition) {
        this.currentEpreuvePosition = currentEpreuvePosition;
    }
}

