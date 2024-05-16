package services;

import entities.Message;

import entities.Notification;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessage implements IService<Message> {

    Connection connection;

    public ServiceMessage() {
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public  void ajouter(Message msg) throws SQLException {
        if (connection == null) {
            System.out.println("La connexion à la base de données est nulle.");
            return;
        }

        String req = "insert into message (date_envoie, date_reception, contenue) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, msg.getDate_envoie());
        preparedStatement.setString(2, msg.getDate_reception());
        preparedStatement.setString(3, msg.getContenue());
        preparedStatement.executeUpdate();
        System.out.println("Message ajouté");
    }

    @Override
    public void modifier(Message msg) throws SQLException {
        String req="update msg set Date_envoie=?,Date_reception=?,contenue=?, where id=?";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setString(1,msg.getDate_envoie());
        preparedStatement.setString(2,msg.getDate_reception());
        preparedStatement.setString(3, msg.getContenue());
        preparedStatement.setInt(4,msg.getId());
        preparedStatement.executeUpdate();
        System.out.println("updated");



    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req ="delete from message where id="+id;
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);
    }




    @Override
    public List<Message> afficher() throws SQLException {
        List<Message> msgs = new ArrayList<>();
        String req = "select * from message";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            Message msg = new Message();
            msg.setDate_envoie(rs.getString("date_envoie"));
            msg.setDate_reception(rs.getString("date_reception")); // Correction ici
            msg.setContenue(rs.getString("contenue")); // Correction ici
            msg.setId(rs.getInt("id"));
            msgs.add(msg);
        }
        return msgs;
    }

}

