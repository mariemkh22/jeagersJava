package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

    public class test {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField nameTF;

        @FXML
        void ajouterService(ActionEvent event) {


        }

        @FXML
        void initialize() {
            assert nameTF != null : "fx:id=\"nameTF\" was not injected: check your FXML file 'test.fxml'.";

        }

    }


