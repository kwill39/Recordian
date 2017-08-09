package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.controller.MainViewController;
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

public class EditLocationController extends DatabaseItemController implements Initializable {
    private String nameOfLocationToEdit;
    private Location locationToBeEdited;
    private @FXML Button saveButton;
    private @FXML Button cancelButton;
    private @FXML TextField locationName;
    private @FXML TextField locationAddress;
    private @FXML TextField locationCity;
    private @FXML TextField locationState;
    private @FXML TextField locationZipCode;

    public EditLocationController(Stage stage, MainViewController mainViewController) {
        super(stage, mainViewController);
        nameOfLocationToEdit = mainViewController.locationChoiceBox.getValue().toString();
        List<Location> locations = LocationMapper.readAll();
        for (Location someLocation : locations) {
            if (someLocation.getLocationName().equals(nameOfLocationToEdit)){
                locationToBeEdited = someLocation;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateLocation());
        cancelButton.setOnMouseClicked(event -> stage.close());
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
