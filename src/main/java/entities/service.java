package entities;

import javafx.scene.image.Image;

import java.util.Calendar;

public class service {

    private int id;
    private String name_s, description_s, localisation, state, dispo_date;

    private int cat_id;
    private String imageFile;
    public static String searchValue;
    private Calendar date;

    public static String getSearchValue() {
        return searchValue;
    }

    public static void setSearchValue(String searchValue) {
        service.searchValue = searchValue;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_s() {
        return name_s;
    }

    public void setName_s(String name_s) {
        this.name_s = name_s;
    }

    public String getDescription_s() {
        return description_s;
    }

    public void setDescription_s(String description_s) {
        this.description_s = description_s;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDispo_date() {
        return dispo_date;
    }

    public void setDispo_date(String dispo_date) {
        this.dispo_date = dispo_date;
    }


    public service(int id,String name_s, String description_s, String localisation, String state, String dispo_date, int cat_id,String imageFile) {
        this.id = id;
        this.name_s = name_s;
        this.description_s = description_s;
        this.localisation = localisation;
        this.state = state;
        this.dispo_date = dispo_date;
        this.cat_id=cat_id;
        this.imageFile=imageFile;


    }

    public service(String name_s, String description_s, String localisation, String state, String dispo_date, int cat_id, String imageFile
    ) {

        this.name_s = name_s;

        this.description_s = description_s;
        this.localisation = localisation;
        this.state = state;
        this.dispo_date = dispo_date;
        this.cat_id=cat_id;
        this.imageFile= imageFile;

    }

    public service() {
    }

    public Calendar getDate() {
        return date;
    }
}