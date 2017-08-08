package com.kylewill.controller;

import com.kylewill.model.Location;
import com.kylewill.objectrelationalmaps.LocationMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteLocationController implements Initializable{
    private MainViewController mainViewController;
    private Stage stage;
    private String nameOfLocationToDelete;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(event -> stage.close());
        deleteButton.setOnMouseClicked(event -> deleteLocation());
    }

    void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNameOfLocationToDelete(String nameOfLocationToDelete) {
        this.nameOfLocationToDelete = nameOfLocationToDelete;
    }

    private void deleteLocation() {
        List<Location> companies = LocationMapper.readAll();
        for (Location someLocation : companies){
            if (someLocation.getLocationName().equals(nameOfLocationToDelete)){
                LocationMapper.delete(someLocation);
            }
        }
        mainViewController.refreshLocationNames();
        stage.close();
    }
}
