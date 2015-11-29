package be.ipl.groupe17.jeudepiste;

/**
 * Created by Alexandre on 26-11-15.
 */
public class Epreuve {
    //TODO : à compléter
    private String nom;
    private boolean completed;
    private Zone zone;

    public Epreuve() {
    }

    public Epreuve(String nom, boolean completed, Zone zone) {
        this.nom = nom;
        this.completed = completed;
        this.zone = zone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
