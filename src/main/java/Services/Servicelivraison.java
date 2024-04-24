package Services;
import Entity.Livraison;
import Entity.LocalisationGeographique;
import utils.MyDatabase;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class Servicelivraison implements IService<Livraison> {
    Connection connection;

    public Servicelivraison() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Livraison livraison) throws SQLException {
        String queryRegion = "SELECT id FROM localisation_geographique WHERE region=?";
        int localisationGeographiqueId;

        try (PreparedStatement preparedStatementRegion = connection.prepareStatement(queryRegion)) {
            preparedStatementRegion.setString(1, livraison.getLocalisationGeographique().getRegion());
            ResultSet resultSetRegion = preparedStatementRegion.executeQuery();

            if (resultSetRegion.next()) {
                localisationGeographiqueId = resultSetRegion.getInt("id");
            } else {
                // La région n'existe pas dans la base de données
                throw new SQLException("La région spécifiée n'existe pas dans la base de données.");
            }
        }

        String queryLivraison = "INSERT INTO livraison (date_debut, date_fin, entreprise, frais, status, localisation_geographique_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatementLivraison = connection.prepareStatement(queryLivraison, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatementLivraison.setDate(1, new java.sql.Date(livraison.getDate_debut().getTime()));
            preparedStatementLivraison.setDate(2, new java.sql.Date(livraison.getDate_fin().getTime()));
            preparedStatementLivraison.setString(3, livraison.getEntreprise());
            preparedStatementLivraison.setInt(4, livraison.getFrais());
            preparedStatementLivraison.setString(5, livraison.getStatus());
            preparedStatementLivraison.setInt(6, localisationGeographiqueId);
            preparedStatementLivraison.executeUpdate();

            ResultSet generatedKeys = preparedStatementLivraison.getGeneratedKeys();
            if (generatedKeys.next()) {
                livraison.setId(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public void modifier(Livraison livraison) throws SQLException {
        String query = "UPDATE livraison SET date_debut=?, date_fin=?, entreprise=?, frais=?, status=? WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(livraison.getDate_debut().getTime()));
            preparedStatement.setDate(2, new java.sql.Date(livraison.getDate_fin().getTime()));
            preparedStatement.setString(3, livraison.getEntreprise());
            preparedStatement.setInt(4, livraison.getFrais());
            preparedStatement.setString(5, livraison.getStatus());
            preparedStatement.setInt(6, livraison.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {

        String query = "DELETE FROM livraison WHERE id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Livraison> afficher(Livraison livraison) throws SQLException {
        List<Livraison> livraisons = new ArrayList<>();

        String query = "SELECT l.id, l.date_debut, l.date_fin, l.entreprise, l.frais, l.status, lg.id AS localisation_id, lg.region " +
                "FROM livraison l " +
                "INNER JOIN localisation_geographique lg ON l.localisation_geographique_id = lg.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Livraison liv = new Livraison();
                liv.setId(resultSet.getInt("id"));
                liv.setDate_debut(resultSet.getDate("date_debut"));
                liv.setDate_fin(resultSet.getDate("date_fin"));
                liv.setEntreprise(resultSet.getString("entreprise"));
                liv.setFrais(resultSet.getInt("frais"));
                liv.setStatus(resultSet.getString("status"));

                // Création de la localisation géographique associée
                LocalisationGeographique localisation = new LocalisationGeographique();
                localisation.setId(resultSet.getInt("localisation_id"));
                localisation.setRegion(resultSet.getString("region"));

                // Attribution de la localisation à la livraison
                liv.setLocalisationGeographique(localisation);

                livraisons.add(liv);
            }
        }

        return livraisons;
    }
    @Override
    public void supprimerToutesLivraisons() throws SQLException {
        String query = "DELETE FROM livraison";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }
}