package com.kylewill.controller;

import com.kylewill.model.Location;
import com.kylewill.objectrelationalmap.LocationMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditLocationController implements Initializable {
    private String nameOfLocationToEdit;
    private MainViewController mainViewController;
    private Stage stage;
    private @FXML Button saveButton;
    private @FXML Button cancelButton;
    private @FXML TextField locationName;
    private @FXML TextField locationAddress;
    private @FXML TextField locationCity;
    private @FXML TextField locationState;
    private @FXML TextField locationZipCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateLocation());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNameOfLocationToEdit(String nameOfLocationToEdit) {
        this.nameOfLocationToEdit = nameOfLocationToEdit;
        // TODO: Find a better way to set the textfields for location values
        // TODO: currently setting it outside the initialize method
        Location locationToBeEdited = new Location("");
        List<Location> locations = LocationMapper.readAll();
        for (Location someLocation : locations) {
            if (someLocation.getLocationName().equals(nameOfLocationToEdit)){
                locationToBeEdited = someLocation;
            }
        }
        locationName.setText(locationToBeEdited.getLocationName());
        locationAddress.setText(locationToBeEdited.getLocationAddress());
        locationCity.setText(locationToBeEdited.getLocationCity());
        locationState.setText(locationToBeEdited.getLocationState());
        locationZipCode.setText(locationToBeEdited.getLocationZipCode());
    }

    private void updateLocation() {
        List<Location> locations = LocationMapper.readAll();
        for (Location someLocation : locations) {
            if (someLocation.getLocationName().equals(nameOfLocationToEdit)){
                someLocation.setLocationName(locationName.getText());
                someLocation.setLocationAddress(locationAddress.getText());
                someLocation.setLocationCity(locationCity.getText());
                someLocation.setLocationState(locationState.getText());
                someLocation.setLocationZipCode(locationZipCode.getText());
                LocationMapper.update(someLocation);
            }
        }
        mainViewController.refreshLocationNames();
        stage.close();
    }
}
