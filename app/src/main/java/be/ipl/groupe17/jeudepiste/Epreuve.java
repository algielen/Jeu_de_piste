package be.ipl.groupe17.jeudepiste;

/**
 * Created by Alexandre on 26-11-15.
 */
public class Epreuve {
    //TODO : à compléter
    private String num;
    private boolean completed;
    private Zone zone;
    private String type;

    public Epreuve() {
    }

    public Epreuve(String num, boolean completed, Zone zone, String type) {
        this.num = num;
        this.completed = completed;
        this.zone = zone;
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
