package services;

import entities.service;
import entities.categorie_service;

import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceService implements IService <service> {


    Connection connection;

    public ServiceService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(service service) throws SQLException {
        String req = "insert into service (name_s,description_s,localisation,state,dispo_date,categorie_id, imageFile)" +
                "values(?,?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(req);
        statement.setString(1, service.getName_s());
        statement.setString(2, service.getDescription_s());
        statement.setString(3, service.getLocalisation());
        statement.setString(4, service.getState());
        statement.setString(5, service.getDispo_date());
        statement.setInt(6, service.getCat_id());
        statement.setString(7, service.getImageFile());
        statement.executeUpdate(); //exécuter requete
        System.out.println("service ajoute");
    }

    @Override
    public void modifier(service service) throws SQLException {
        String req = "update service set name_s=?, description_s=?, localisation=?, state=?, dispo_date=?, categorie_id=?, imageFile=? where id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, service.getName_s());
        preparedStatement.setString(2, service.getDescription_s());
        preparedStatement.setString(3, service.getLocalisation());
        preparedStatement.setString(4, service.getState());
        preparedStatement.setString(5, service.getDispo_date());
        preparedStatement.setInt(6, service.getCat_id());
        preparedStatement.setString(7, service.getImageFile());

        preparedStatement.setInt(8, service.getId());
        preparedStatement.executeUpdate();
        System.out.println("updated");

    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req = "delete from service where id=" + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);

    }

    @Override
    public List<service> afficher() throws SQLException {
        List<service> servicesL = new ArrayList<>();
        String req = "select * from service";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            service service = new service();
            service.setId(rs.getInt("id"));
            service.setName_s(rs.getString("name_s"));
            service.setDescription_s(rs.getString("description_s"));
            service.setLocalisation(rs.getString("localisation"));
            service.setState(rs.getString("state"));
            service.setDispo_date(rs.getString("dispo_date"));

            service.setCat_id(rs.getInt("categorie_id"));
            service.setImageFile(rs.getString("imageFile"));


            servicesL.add(service);

        }
        return servicesL;
    }


    public List<service> searchService(String search) throws SQLException {
        List<service> services = new ArrayList<>();
        String query = "SELECT * FROM service WHERE name_s LIKE ? OR description_s LIKE ? OR localisation LIKE ? OR state LIKE ? OR dispo_date LIKE ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%" + search + "%");
        preparedStatement.setString(2, "%" + search + "%");
        preparedStatement.setString(3, "%" + search + "%");
        preparedStatement.setString(4, "%" + search + "%");
        preparedStatement.setString(5, "%" + search + "%");


        ResultSet resultSet = preparedStatement.executeQuery();

        // Parcours du résultat de la requête
        while (resultSet.next()) {
            service service = new service();
            service.setId(resultSet.getInt("id"));
            service.setName_s(resultSet.getString("name_s"));
            service.setDescription_s(resultSet.getString("description_s"));
            service.setLocalisation(resultSet.getString("localisation"));
            service.setState(resultSet.getString("state"));
            service.setDispo_date(resultSet.getString("dispo_date"));
            service.setCat_id(resultSet.getInt("categorie_id"));
            service.setImageFile(resultSet.getString("imageFile"));

            services.add(service);
        }
        preparedStatement.close();

        return services;
    }

    public List<service> getServiceByCategorie(int cat_id) throws SQLException {
        List<service> services = new ArrayList<>();
        String query = "SELECT * FROM service WHERE categorie_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, cat_id);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Parcours du résultat de la requête
        while (resultSet.next()) {
            service service = new service();
            service.setId(resultSet.getInt("id"));
            service.setName_s(resultSet.getString("name_s"));
            service.setDescription_s(resultSet.getString("description_s"));
            service.setLocalisation(resultSet.getString("localisation"));
            service.setState(resultSet.getString("state"));
            service.setDispo_date(resultSet.getString("dispo_date"));
            service.setCat_id(resultSet.getInt("categorie_id"));
            service.setImageFile(resultSet.getString("imageFile"));

            services.add(service);
        }
        preparedStatement.close();

        return services;
    }
}







