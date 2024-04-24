package tests;

import entities.categorie_service;
import entities.service;
import services.ServiceCategorie;
import services.ServiceService;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {



        categorie_service category = new categorie_service("category13333", "description13");
        categorie_service category1 = new categorie_service(31,"UPDATEEEED", "UPDATEEED");
        categorie_service category2 = new categorie_service(31,"cat123", "cat12345");

        ServiceCategorie serviceCategorie = new ServiceCategorie();

        try {

            //serviceCategorie.ajouter(category2);
            serviceCategorie.modifier(category1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(serviceCategorie.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {

            serviceCategorie.supprimer(32);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }


        service service = new service("ser", "dec", "gabes", "av", "12/12/2012", 1, null);
        service service1 = new service("ser1", "des1", "tunis", "av", "12/12/2021", 2,null);





        ServiceService serviceService = new ServiceService();

        try {

            serviceService.modifier(service);
            //serviceService.ajouter(service1);
            //serviceService.ajouter(service2);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        try {

            serviceService.supprimer(42);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(serviceService.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }










    }
}