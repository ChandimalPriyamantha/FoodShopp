package com.example.foodshope_j;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppController.class.getResource("Login&Registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setTitle("ARACHCHI (J) MANAGEMENT SYSTEM");
        Image image = new Image(getClass().getResourceAsStream("/appIcon.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}