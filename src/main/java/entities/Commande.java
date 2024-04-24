package entities;

import java.util.Date;
public class Commande {


    private int id;
    private Date DateCmd ;
    private String methode_livraison;
    private String ville;
    private Produit produit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCmd() {
        return DateCmd;
    }

    public void setDateCmd(Date dateCmd) {
        DateCmd = dateCmd;
    }

    public String getMethode_livraison() {
        return methode_livraison;
    }

    public void setMethode_livraison(String methode_livraison) {
        this.methode_livraison = methode_livraison;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande() {
    }

    public Commande(Date dateCmd, String methode_livraison, String ville, Produit produit) {
        DateCmd = dateCmd;
        this.methode_livraison = methode_livraison;
        this.ville = ville;
        this.produit = produit;
    }

    public Commande(int id, Date dateCmd, String methode_livraison, String ville, Produit produit) {
        this.id = id;
        DateCmd = dateCmd;
        this.methode_livraison = methode_livraison;
        this.ville = ville;
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", DateCmd=" + DateCmd +
                ", methode_livraison='" + methode_livraison + '\'' +
                ", ville='" + ville + '\'' +
                ", produit=" + produit +
                '}';
    }
}
