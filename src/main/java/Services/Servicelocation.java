package Services;

import Entity.LocalisationGeographique;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicelocation implements ILocation<LocalisationGeographique>{
    Connection connection;
    public Servicelocation(){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouterLocation(LocalisationGeographique localisationGeo) throws SQLException {
        String query = "INSERT INTO localisation_geographique (region, codepostal, adresse) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, localisationGeo.getRegion());
            preparedStatement.setInt(2, localisationGeo.getCodepostal());
            preparedStatement.setString(3, localisationGeo.getAdresse());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifierLocation(LocalisationGeographique localisationGeo) throws SQLException {
        String query = "UPDATE localisation_geographique SET region=?, codepostal=?, adresse=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, localisationGeo.getRegion());
            preparedStatement.setInt(2, localisationGeo.getCodepostal());
            preparedStatement.setString(3, localisationGeo.getAdresse());
            preparedStatement.setInt(4, localisationGeo.getId());
            preparedStatement.executeUpdate();
        }

    }

    @Override
    public void supprimerLocation(int id) throws SQLException {
        String query = "DELETE FROM localisation_geographique WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }

    }

    @Override
    public void supprimerToutesLocations() throws SQLException {
        String query = "DELETE FROM localisation_geographique";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<LocalisationGeographique> afficherLocation() throws SQLException {
        List<LocalisationGeographique> locations = new ArrayList<>();
        String req = "SELECT * FROM localisation_geographique";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            LocalisationGeographique location = new LocalisationGeographique();
            location.setId(rs.getInt("id"));
            location.setRegion(rs.getString("region"));
            location.setCodepostal(rs.getInt("codepostal"));
            location.setAdresse(rs.getString("adresse"));
            locations.add(location);
        }
        return locations;
    }

    @Override
    public void supprimerLivraisonsParLocalisation(int localisationId) throws SQLException {
        String query = "DELETE FROM livraison WHERE localisation_geographique_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, localisationId);
            preparedStatement.executeUpdate();
        }
    }
}
