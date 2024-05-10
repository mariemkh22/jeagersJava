package entities;

public class LocalisationGeographique {
    private int id;
    private String region;
    private int codepostal;
    private String adresse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(int codepostal) {
        this.codepostal = codepostal;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalisationGeographique(int id, String region, int codepostal, String adresse) {
        this.id = id;
        this.region = region;
        this.codepostal = codepostal;
        this.adresse = adresse;
    }

    public LocalisationGeographique(String region, int codepostal, String adresse) {
        this.region = region;
        this.codepostal = codepostal;
        this.adresse = adresse;
    }

    public LocalisationGeographique() {
    }

    @Override
    public String toString() {
        return "LocalisationGeo{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", codepostal=" + codepostal +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
