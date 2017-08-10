package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Location;
import com.kylewill.objectrelationalmap.LocationMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteLocationController extends DatabaseItemController implements Initializable{
    private LocationMapper locationMapper = new LocationMapper();
    private Location locationToDelete;
    @FXML private Label confirmationLabel;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(event -> stage.close());
        deleteButton.setOnMouseClicked(event -> deleteLocation());
    }

    @Override
    protected void onMainViewControllerSet() {
        String nameOfLocationToDelete = mainViewController.locationChoiceBox.getValue().toString();
        List<Location> companies = locationMapper.readAll();
        for (Location someLocation : companies){
            if (someLocation.getLocationName().equals(nameOfLocationToDelete)){
                locationToDelete = someLocation;
            }
        }
        confirmationLabel.setText("Are you sure you want to delete " + locationToDelete.getLocationName() + "?");
    }

    private void deleteLocation() {
        locationMapper.delete(locationToDelete);
        mainViewController.refreshLocationNames();
        stage.close();
    }
}
