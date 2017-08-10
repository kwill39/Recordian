package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Location;
import com.kylewill.objectrelationalmap.LocationMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditLocationController extends DatabaseItemController implements Initializable {
    private LocationMapper locationMapper = new LocationMapper();
    private Location locationToEdit;
    private @FXML TextField locationName;
    private @FXML TextField locationAddress;
    private @FXML TextField locationCity;
    private @FXML TextField locationState;
    private @FXML TextField locationZipCode;
    private @FXML Button saveButton;
    private @FXML Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateLocation());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    @Override
    protected void onMainViewControllerSet() {
        String nameOfLocationToEdit = mainViewController.locationChoiceBox.getValue().toString();
        List<Location> locations = locationMapper.readAll();
        for (Location someLocation : locations) {
            if (someLocation.getLocationName().equals(nameOfLocationToEdit)){
                locationToEdit = someLocation;
            }
        }
        locationName.setText(locationToEdit.getLocationName());
        locationAddress.setText(locationToEdit.getLocationAddress());
        locationCity.setText(locationToEdit.getLocationCity());
        locationState.setText(locationToEdit.getLocationState());
        locationZipCode.setText(locationToEdit.getLocationZipCode());
    }

    private void updateLocation() {
        locationToEdit.setLocationName(locationName.getText());
        locationToEdit.setLocationAddress(locationAddress.getText());
        locationToEdit.setLocationCity(locationCity.getText());
        locationToEdit.setLocationState(locationState.getText());
        locationToEdit.setLocationZipCode(locationZipCode.getText());
        locationMapper.update(locationToEdit);
        mainViewController.refreshLocationNames();
        stage.close();
    }
}
