package services;

import entities.Produit;
import services.IService;
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
        String req = "insert into produit (nom_produit,type,description,equiv,imageFile)" +
                "values(?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(req);
        statement.setString(1, produit.getNomProduit());
        statement.setString(2, produit.getType());
        statement.setString(3, produit.getDescription());
        statement.setFloat(4, produit.getEquiv());
        statement.setString(5, produit.getImageFile());

        statement.executeUpdate();
        System.out.println("Product added");
    }

    @Override
    public void modifier(Produit produit) throws SQLException {
        String req = "update produit set nom_produit=?,type=?,description=?,equiv=?,imageFile=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, produit.getNomProduit());
        preparedStatement.setString(2, produit.getType());
        preparedStatement.setString(3, produit.getDescription());
        preparedStatement.setFloat(4, produit.getEquiv());
        preparedStatement.setString(5, produit.getImageFile());
        preparedStatement.setInt(6, produit.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "delete from produit where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
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
                produit.setImageFile(rs.getString("imageFile"));

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

    public static List<Produit> getProducts() {
        ServiceProduit serviceProduit = new ServiceProduit();
        try {
            return serviceProduit.afficher();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
