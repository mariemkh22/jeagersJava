package tests;

import entities.Notification;
import services.ServiceNotification;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {


       MyDatabase.getInstance().getConnection();
       Connection connection =MyDatabase.getInstance().getConnection();
        ServiceNotification serviceNotification = new ServiceNotification();
        Notification notification = new Notification("remise", "offre","17/02/2024");

        try {
            serviceNotification.ajouter(notification);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
