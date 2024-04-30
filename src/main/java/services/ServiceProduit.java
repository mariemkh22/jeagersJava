package services;


import entities.Produit;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements IService<Produit> {
    Connection connection;

    public ServiceProduit() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Produit produit) throws SQLException {
        String req = "insert into produit (nom_produit,type,description,equiv)" +
                "values('" + produit.getNomProduit() + "','" + produit.getType() + "','" + produit.getDescription() + "'," + produit.getEquiv() + ")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("Product added");
    }

    @Override
    public void modifier(Produit produit) throws SQLException {
        String req = "update produit set nom_produit=?,type=?,description=?,equiv=? where id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, produit.getNomProduit());
        preparedStatement.setString(2, produit.getType());
        preparedStatement.setString(3, produit.getDescription());
        preparedStatement.setFloat(4, produit.getEquiv());
        preparedStatement.setInt(5, produit.getId());


        preparedStatement.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req = "delete from produit where id=" + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);

    }


    @Override
    public List<Produit> afficher() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String req = "select * from produit";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(req)) {
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setNomProduit(rs.getString("nom_produit"));
                produit.setType(rs.getString("type"));
                produit.setDescription(rs.getString("description"));
                produit.setEquiv(rs.getFloat("equiv"));

                produits.add(produit);
            }
        }
        return produits;
    }




    public void deleteAll() throws SQLException {
        String req = "DELETE FROM produit";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(req);
            System.out.println("All products deleted");
        }
    }
}
