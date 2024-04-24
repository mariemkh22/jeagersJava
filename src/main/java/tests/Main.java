package tests;


import java.util.Date;


import entities.Produit;
import services.ServiceProduit;

import entities.Commande;
import services.ServiceCommande;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Création d'un service de produit
            ServiceProduit serviceProduit = new ServiceProduit();
            // Création d'un produit
            Produit nouveauProduit = new Produit();
            nouveauProduit.setNomProduit("Nom du produit");
            nouveauProduit.setType("Type du produit");
            nouveauProduit.setDescription("Description du produit");
            nouveauProduit.setEquiv(10.5f); // Prix du produit
            // Ajout du produit à la base de données
            serviceProduit.ajouter(nouveauProduit);
            System.out.println("Produit ajouté avec succès!");

            // Création d'un service de commande
            ServiceCommande serviceCommande = new ServiceCommande();
            // Création d'une nouvelle commande
            Commande nouvelleCommande = new Commande();
            nouvelleCommande.setDateCmd(new Date());
            nouvelleCommande.setMethode_livraison("express");
            nouvelleCommande.setVille("Paris");
            // Associer le produit créé à la commande
            nouvelleCommande.setProduit(nouveauProduit);
            // Ajout de la commande avec le produit associé
            serviceCommande.ajouter(nouvelleCommande);
            System.out.println("Commande ajoutée avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Une erreur s'est produite lors de l'interaction avec la base de données.");
        }
    }
}