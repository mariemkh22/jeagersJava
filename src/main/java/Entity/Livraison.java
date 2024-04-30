package Entity;

import java.util.Date;
import java.util.Objects;

public class Livraison {
    private int id;
    private Date date_debut;
    private Date date_fin;
    private String entreprise;
    private int frais;
    private String status;
    private LocalisationGeographique localisationGeographique;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public int getFrais() {
        return frais;
    }

    public void setFrais(int frais) {
        this.frais = frais;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public LocalisationGeographique getLocalisationGeographique() {return localisationGeographique;}

    public void setLocalisationGeographique(LocalisationGeographique localisationGeographique) {this.localisationGeographique = localisationGeographique;}

    public Livraison(int id, Date date_debut, Date date_fin, String entreprise, int frais, String status, LocalisationGeographique localisationGeographique) {
        this.id = id;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.entreprise = entreprise;
        this.frais = frais;
        this.status = status;
        this.localisationGeographique = localisationGeographique;
    }

    public Livraison(){}

    public Livraison(Date date_debut, Date date_fin, String entreprise, int frais, String status,LocalisationGeographique localisationGeographique) {
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.entreprise = entreprise;
        this.frais = frais;
        this.status = status;
        this.localisationGeographique = localisationGeographique;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if(o instanceof Livraison){
            final Livraison livraison = (Livraison) o;
            return this.status == livraison.status;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date_debut, date_fin, entreprise, frais, status, localisationGeographique);
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", entreprise='" + entreprise + '\'' +
                ", frais=" + frais +
                ", status='" + status + '\'' +
                ", localisationGeographique=" + localisationGeographique +
                '}';
    }
}
