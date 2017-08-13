package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Location;
import com.kylewill.objectrelationalmap.LocationMapper;
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
public class EditLocationController extends DatabaseItemModificationController implements Initializable {
    private LocationMapper locationMapper = new LocationMapper();
    private Location locationToEdit;
    @FXML private TextField locationName;
    @FXML private TextField locationAddress;
    @FXML private TextField locationCity;
    @FXML private TextField locationState;
    @FXML private TextField locationZipCode;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateLocation());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    @Override
    protected void onMainViewControllerSet() {
        String nameOfLocationToEdit = mainViewController.locationChoiceBox.getValue().toString();
        locationToEdit = locationMapper.read(nameOfLocationToEdit);
        locationName.setText(locationToEdit.getLocationName());
        locationAddress.setText(locationToEdit.getLocationAddress());
        locationCity.setText(locationToEdit.getLocationCity());
        locationState.setText(locationToEdit.getLocationState());
        locationZipCode.setText(locationToEdit.getLocationZipCode());
    }

    private void updateLocation() {
        if (locationName.getText().isEmpty()
                || locationAddress.getText().isEmpty()
                || locationCity.getText().isEmpty()
                || locationState.getText().isEmpty()
                || locationZipCode.getText().isEmpty()) {
            errorLabel.setVisible(true);
        } else {
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
}
