package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Location;
import com.kylewill.objectrelationalmap.LocationMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteLocationController extends DatabaseItemController implements Initializable{
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(event -> stage.close());
        deleteButton.setOnMouseClicked(event -> deleteLocation());
    }

    private void deleteLocation() {
        String nameOfLocationToDelete = mainViewController.locationChoiceBox.getValue().toString();
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
