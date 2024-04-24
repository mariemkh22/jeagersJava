package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class  Notification {
    private int id;
    private String contenue;
    private String sujet;
    private String date_envoie;



    public Notification(int id,String contenue,String sujet ,String date_envoie) {
        this.id = id;
        this.contenue = contenue;
        this.sujet = sujet;
        this.date_envoie = date_envoie;
    }

    public Notification() {
    }

    public Notification(String contenue,String sujet,String date_envoie) {
        this.contenue = contenue;
        this.sujet = sujet;
        this.date_envoie = date_envoie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDate_envoie() {
        return date_envoie;
    }

    public void setDate_envoie(String date_envoie) {
        this.date_envoie = date_envoie;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", contenue='" + contenue + '\'' +
                ", sujet='" + sujet + '\'' +
                ", date_envoie=" + date_envoie +
                '}';
    }
}
