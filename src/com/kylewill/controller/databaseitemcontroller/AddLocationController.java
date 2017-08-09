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
import java.util.ResourceBundle;

public class AddLocationController extends DatabaseItemController implements Initializable{
    @FXML private TextField newLocationName;
    @FXML private TextField newLocationAddress;
    @FXML private TextField newLocationCity;
    @FXML private TextField newLocationState;
    @FXML private TextField newLocationZipCode;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(event -> addLocation());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    private void addLocation() {
        Location newLocation = new Location(newLocationName.getText());
        newLocation.setLocationAddress(newLocationAddress.getText());
        newLocation.setLocationCity(newLocationCity.getText());
        newLocation.setLocationState(newLocationState.getText());
        newLocation.setLocationZipCode(newLocationZipCode.getText());
        LocationMapper.create(newLocation);
        mainViewController.refreshLocationNames();
        stage.close();
    }
}
