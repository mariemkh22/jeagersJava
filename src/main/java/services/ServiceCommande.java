package services;


import entities.Commande;
import entities.Produit;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceCommande implements IService<Commande> {


    Connection connection;

    public ServiceCommande() {
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Commande commande) throws SQLException {

        String queryProduit = "SELECT id FROM produit WHERE nom_produit=?";
        int produitId;

        try (PreparedStatement pstmtProduit = connection.prepareStatement(queryProduit)) {
            pstmtProduit.setString(1, commande.getProduit().getNomProduit());
            ResultSet resultSetProduit = pstmtProduit.executeQuery();

            if (resultSetProduit.next()) {
                produitId = resultSetProduit.getInt("id");
            } else {

                throw new SQLException("Le produit spécifié n'existe pas dans la base de données.");
            }
        }


        String queryCommande = "INSERT INTO commande (date_cmd, methode_livraison, ville, produit_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmtCommande = connection.prepareStatement(queryCommande, Statement.RETURN_GENERATED_KEYS)) {
            pstmtCommande.setDate(1, new java.sql.Date(commande.getDateCmd().getTime()));
            pstmtCommande.setString(2, commande.getMethode_livraison());
            pstmtCommande.setString(3, commande.getVille());
            pstmtCommande.setInt(4, produitId);
            pstmtCommande.executeUpdate();

            ResultSet generatedKeys = pstmtCommande.getGeneratedKeys();
            if (generatedKeys.next()) {
                commande.setId(generatedKeys.getInt(1));
            }
        }
    }



    @Override
    public void modifier(Commande commande) throws SQLException {
        String query = "UPDATE commande SET date_cmd=?, methode_livraison=?, ville=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(commande.getDateCmd().getTime()));
            preparedStatement.setString(2, commande.getMethode_livraison());
            preparedStatement.setString(3, commande.getVille());
            preparedStatement.setInt(4, commande.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM commande WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Commande> afficher() throws SQLException {
        List<Commande> commandes = new ArrayList<>();

        String query = "SELECT c.id, c.date_cmd, c.methode_livraison, c.ville, p.id AS produit_id, p.nom_produit " +
                "FROM commande c " +
                "INNER JOIN produit p ON c.produit_id = p.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.setId(resultSet.getInt("id"));
                commande.setDateCmd(resultSet.getDate("date_cmd")); // Correction de la colonne ici
                commande.setMethode_livraison(resultSet.getString("methode_livraison"));
                commande.setVille(resultSet.getString("ville"));

                // Création du produit associé
                Produit produit = new Produit();
                produit.setId(resultSet.getInt("produit_id"));
                produit.setNomProduit(resultSet.getString("nom_produit"));

                // Attribution du produit à la commande
                commande.setProduit(produit);

                commandes.add(commande);
            }
        }

        return commandes;
    }

    public void deleteAllC() throws SQLException {
        String req = "DELETE FROM commande";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(req);
            System.out.println("All Orders deleted");
        }
    }

}