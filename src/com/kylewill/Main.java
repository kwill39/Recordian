package com.kylewill;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create a new database file if one does not currently exist
        DatabaseHelper.createDatabase();

        Parent root = FXMLLoader.load(getClass().getResource("view/mainView.fxml"));
        primaryStage.setTitle("Kyle's Hour Tracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
