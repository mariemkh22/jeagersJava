package services;

import entities.Message;

import utils.MyDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServiceMessage implements IService<Message> {

    Connection connection;

    public ServiceMessage() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Message msg) throws SQLException {
        if (connection == null) {
            System.out.println("La connexion à la base de données est nulle.");
            return;
        }
    }

    @Override
    public void modifier(Message message) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<Message> afficher() throws SQLException {
        return null;
    }
}

