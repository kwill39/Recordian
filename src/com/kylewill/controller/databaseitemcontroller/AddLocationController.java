package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Location;
import com.kylewill.databasemanagement.objectrelationalmap.LocationMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class AddLocationController extends DatabaseItemModificationController implements Initializable{
    private LocationMapper locationMapper = new LocationMapper();
    @FXML private TextField newLocationName;
    @FXML private TextField newLocationAddress;
    @FXML private TextField newLocationCity;
    @FXML private TextField newLocationState;
    @FXML private TextField newLocationZipCode;
    @FXML private Label errorLabel;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(event -> addLocation());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    private void addLocation() {
        if (newLocationName.getText().isEmpty()
                || newLocationAddress.getText().isEmpty()
                || newLocationCity.getText().isEmpty()
                || newLocationState.getText().isEmpty()
                || newLocationZipCode.getText().isEmpty()) {
            errorLabel.setVisible(true);
        } else {
            Location newLocation = new Location(newLocationName.getText());
            newLocation.setLocationAddress(newLocationAddress.getText());
            newLocation.setLocationCity(newLocationCity.getText());
            newLocation.setLocationState(newLocationState.getText());
            newLocation.setLocationZipCode(newLocationZipCode.getText());
            locationMapper.create(newLocation);
            mainViewController.refreshLocationNames();
            mainViewController.locationChoiceBox.setValue(newLocation.getLocationName());
            stage.close();
        }
    }
}
