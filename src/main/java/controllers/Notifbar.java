package controllers;

import entities.Notification;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.ServiceNotification;

import java.sql.SQLException;


public class Notifbar {
    private final ServiceNotification ServiceNotification=new ServiceNotification();
    public void notifymeAction(ActionEvent actionEvent) throws SQLException {
        Image image=new Image("noti1.png");
        image.widthProperty();

        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Hello i have a good news for you SwapCraze have a new product check it !");
        notifications.title("New Notification !!");
        notifications.hideAfter(Duration.seconds(6));
        notifications.darkStyle();
        notifications.show();

    }
}
