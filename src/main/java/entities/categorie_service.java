package entities;

public class categorie_service {
    private int id;
    private  String name_c;
    private String description_c;

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getName_c() {
        return name_c;
    }

    public void setName_c(String name_c) {
        this.name_c = name_c;
    }

    public  String getDescription_c() {
        return description_c;
    }

    public void setDescription_c(String description_c) {
        this.description_c = description_c;
    }

    public categorie_service(int id, String name_c, String description_c) {
        this.id = id;
        this.name_c = name_c;
        this.description_c = description_c;
    }

    public categorie_service(String name_c, String description_c) {

        this.name_c = name_c;
        this.description_c = description_c;
    }

    public categorie_service() {
    }

    @Override
    public String toString() {
        return name_c;
    }
}
