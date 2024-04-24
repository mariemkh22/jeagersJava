package services;

import java.sql.*;

import entities.categorie_service;
import utils.MyDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceCategorie implements IService <categorie_service>{

    Connection connection;


    public ServiceCategorie(){
        connection= MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(categorie_service categorieService) throws SQLException {

        String req ="insert into categorie_service (name_c,description_c)"+
                "values('"+categorieService.getName_c()+"', '"+categorieService.getDescription_c()+"')";

        Statement statement= connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("categorie ajoute");

    }

    @Override
    public void modifier(categorie_service categorieService) throws SQLException {

        String req ="update categorie_service set name_c=?, description_c=? where id=?";

        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setString(1, categorieService.getName_c());
        preparedStatement.setString(2, categorieService.getDescription_c());
        preparedStatement.setInt(3, categorieService.getId());

        preparedStatement.executeUpdate();
        System.out.println("updated");


    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req ="delete from categorie_service where id="+id;
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public List<categorie_service> afficher() throws SQLException {

        List<categorie_service> categories= new ArrayList<>();
        String req ="select * from categorie_service";
        Statement statement= connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()){
            categorie_service categorie = new categorie_service();

            categorie.setName_c(rs.getString("name_c"));
            categorie.setDescription_c(rs.getString("description_c"));
            categorie.setId(rs.getInt("id"));

            categories.add(categorie);

        }
        return categories;
    }


}


