package tests;
import Entity.Livraison;
import Services.Servicelivraison;
import Services.Servicelocation;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import Entity.LocalisationGeographique;

public class Main {
    public static void main(String[] args) {
        Servicelocation service = new Servicelocation();
        Servicelivraison serviceLivraison = new Servicelivraison();
/*
        // Ajouter une localisation
        try {
            LocalisationGeographique newLocation = new LocalisationGeographique("tunisie", 75000, "fatnassa");
            service.ajouterLocation(newLocation);
            System.out.println("Localisation ajoutée avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la localisation: " + e.getMessage());
        }

        // Ajouter une lvraison

        // Création d'une instance de Livraison avec uniquement la région de la LocalisationGeographique
        Livraison livraison = new Livraison();
        Calendar calendarDebut = new GregorianCalendar(1948, Calendar.JANUARY, 1);
        Date dateDebut = calendarDebut.getTime();
        Calendar calendarFin = new GregorianCalendar(2027, Calendar.JANUARY, 2);
        Date dateFin = calendarFin.getTime();
        livraison.setDate_debut(dateDebut);
        livraison.setDate_fin(dateFin);
        livraison.setEntreprise("hp");
        livraison.setFrais(100);
        livraison.setStatus("En cours");

        // Supposons que vous avez récupéré la région de la base de données ou fourni une région
        String region = "tunisie";
        LocalisationGeographique localisationGeographique = new LocalisationGeographique(region, 0, ""); // Vous pouvez mettre des valeurs fictives pour le code postal et l'adresse

        // Définir la LocalisationGeographique de la Livraison
        livraison.setLocalisationGeographique(localisationGeographique);

        try {
            // Appel de la méthode ajouter
            serviceLivraison.ajouter(livraison);
            System.out.println("Livraison ajoutée avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la livraison: " + e.getMessage());
        }
/*
        // Création d'une instance de la livraison à modifier
        Livraison livraison = new Livraison();
        livraison.setId(34);
        Calendar calendarDebut = new GregorianCalendar(2020, Calendar.JANUARY, 1);
        Date dateDebut = calendarDebut.getTime();
        Calendar calendarFin = new GregorianCalendar(2024, Calendar.JANUARY, 2);
        Date dateFin = calendarFin.getTime();
        livraison.setDate_debut(dateDebut);
        livraison.setDate_fin(dateFin);
        livraison.setEntreprise("apple"); // Nom de l'entreprise
        livraison.setFrais(20); // Frais de livraison
        livraison.setStatus("terminer"); // Statut de la livraison


        try {
            // Modifier la livraison
            serviceLivraison.modifier(livraison);
            System.out.println("Livraison modifiée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la livraison : " + e.getMessage());
        }

        // ID de la livraison à supprimer
        int livraisonId = 35; // Remplacez ceci par l'ID de la livraison que vous souhaitez supprimer

        // Création d'une instance de la classe Servicelivraison
        Servicelivraison servicelivraison = new Servicelivraison();
        try {
            // Supprimer la livraison
            servicelivraison.supprimer(livraisonId);
            System.out.println("La livraison a été supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la suppression de la livraison : " + e.getMessage());
        }
/*


        try {
            // Appel de la méthode afficher pour récupérer toutes les livraisons
            List<Livraison> livraisons = serviceLivraison.afficher(null); // Passer null si aucun paramètre n'est nécessaire

            // Affichage des livraisons récupérées
            System.out.println("Liste des livraisons :");
            for (Livraison livraison1 : livraisons) {
                System.out.println("ID : " + livraison1.getId());
                System.out.println("Date de début : " + livraison1.getDate_debut());
                System.out.println("Date de fin : " + livraison1.getDate_fin());
                System.out.println("Entreprise : " + livraison1.getEntreprise());
                System.out.println("Frais : " + livraison1.getFrais());
                System.out.println("Status : " + livraison1.getStatus());
                System.out.println("Localisation géographique : " + livraison1.getLocalisationGeographique().getRegion());
                System.out.println(); // Pour une séparation visuelle entre les livraisons
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livraisons : " + e.getMessage());
            e.printStackTrace();
        }
/*
        // Modifier une localisation
        try {
            LocalisationGeographique locationToUpdate = new LocalisationGeographique(31, "Paris", 99, "avignon");
            service.modifierLocation(locationToUpdate);
            System.out.println("Localisation modifiée avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la localisation: " + e.getMessage());
        }

*/
        // Supprimer une localisation
        try {
            int locationIdToDelete = 62;
            service.supprimerLocation(locationIdToDelete);
            System.out.println("Localisation supprimée avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la localisation: " + e.getMessage());
        }
/*
        // Afficher toutes les localisations
        try {
            List<LocalisationGeographique> locations = service.afficherLocation();
            System.out.println("Liste des localisations:");
            for (LocalisationGeographique location : locations) {
                System.out.println(location.getId() + ": " + location.getRegion() + ", " + location.getCodepostal() + ", " + location.getAdresse());

            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des localisations: " + e.getMessage());
        }*/
    }
}