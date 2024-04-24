package entities;

public class Produit {

    private int id;
    private String nom_produit;
    private String type;
    private String description;
    private float equiv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProduit() {
        return nom_produit;
    }

    public void setNomProduit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getEquiv() {
        return equiv;
    }

    public void setEquiv(float equiv) {
        this.equiv = equiv;
    }


    public Produit(int id, String nom_produit, String type, String description, float equiv) {
        this.id = id;
        this.nom_produit = nom_produit;
        this.type = type;
        this.description = description;
        this.equiv = equiv;
    }

    public Produit() {
    }


    public Produit(String nom_produit, String type, String description, float equiv) {
        this.nom_produit = nom_produit;
        this.type = type;
        this.description = description;
        this.equiv = equiv;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom_produit='" + nom_produit + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", equiv=" + equiv +
                '}';
    }
}
