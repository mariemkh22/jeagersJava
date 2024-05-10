package services;

import entities.Notification;
import entities.Notification;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceNotification implements IService<Notification>{
    Connection connection;

    public ServiceNotification(){
        connection= MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Notification notification) throws SQLException {
        if (connection == null) {
            System.out.println("La connexion à la base de données est nulle.");
            return;
        }

        String req = "insert into notification (contenue, sujet, date_envoie) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, notification.getContenue());
        preparedStatement.setString(2, notification.getSujet());
        preparedStatement.setString(3, notification.getDate_envoie());
        preparedStatement.executeUpdate();
        System.out.println("Notification ajoutée");
    }
    @Override
    public void modifier(Notification notification) throws SQLException {
        String req="update notification  set date_envoie=?,sujet=?,contenue=?, where id=?";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setString(1,notification.getDate_envoie());
        preparedStatement.setString(2,notification.getSujet());
        preparedStatement.setString(3,  notification.getContenue());
        preparedStatement.setInt(4,notification.getId());
        preparedStatement.executeUpdate();
        System.out.println("updated");



    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req ="delete from notification where id="+id;
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);

    }

    @Override
    public List<Notification> afficher() throws SQLException {
        List<Notification> notifications= new ArrayList<>();
        String req ="select * from notification";
        Statement statement= connection.createStatement();
        ResultSet rs =statement.executeQuery(req);
        while (rs.next()){
            Notification notification= new Notification();
            notification.setContenue(rs.getString("contenue"));
            notification.setSujet(rs.getString(3));
            notification.setDate_envoie(rs.getString("date_envoie"));
            notification.setId(rs.getInt("id"));

            notifications.add(notification);

        }
        return notifications;
    }
}
