package be.ipl.groupe17.jeudepiste;

import android.location.Location;

/**
 * Représente une zone telle que décrite dans le fichier XML
 */
public class Zone {
    private int rayon;
    private Location location;
    private String nom;


    public Zone(double latitude, double longitude, int rayon, String nom) {
        location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        this.rayon = rayon;
        this.nom = nom;

    }

    public boolean contains(Location target) {
        if (location.distanceTo(target) <= rayon) {
            return true;
        } else {
            return false;
        }
    }

    public float distanceTo(Location target) {
        return location.distanceTo(target);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }
}
