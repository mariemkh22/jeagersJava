package entities;

import java.util.Date;

public class Message {

    private int id;
    private String date_envoie;
    private String date_reception;
    private String contenue;


    public Message(int id,String date_envoie,String date_reception,String contenue) {
        this.id = id;
        this.date_envoie=date_envoie;
        this.date_reception=date_reception;
        this.contenue=contenue;

    }
    public Message(){

    }
    public Message(String date_envoie,String date_reception,String contenue) {
        this.date_envoie=date_envoie;
        this.date_reception=date_reception;
        this.contenue=contenue;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_envoie() {
        return date_envoie;
    }

    public void setDate_envoie(String date_envoie) {
        this.date_envoie = date_envoie;
    }

    public String getDate_reception() {
        return date_reception;
    }

    public void setDate_reception(String date_envoie) {
        this.date_reception = date_reception;
    }


    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }




    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", contenue='" + contenue + '\'' +
                ", date_reception='" + date_reception + '\'' +
                ", date_envoie=" + date_envoie +
                '}';
    }
}
