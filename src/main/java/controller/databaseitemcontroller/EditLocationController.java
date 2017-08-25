package controller.databaseitemcontroller;

import databasemanagement.objectrelationalmap.LocationMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Location;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class EditLocationController extends DatabaseItemModificationController implements Initializable {
    private LocationMapper locationMapper = new LocationMapper();
    private Location locationToEdit;
    private String oldNameOfLocationToEdit;
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
    protected void onNewLogEntryTabControllerSet() {
        oldNameOfLocationToEdit = newLogEntryTabController.locationChoiceBox.getValue().toString();
        locationToEdit = locationMapper.read(oldNameOfLocationToEdit);
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
            errorLabel.setText("Please fill in all fields");
            errorLabel.setVisible(true);
            return;
        }
        List<Location> locations = locationMapper.readAll();
        for (Location location : locations) {
            if (!location.getLocationName().equals(oldNameOfLocationToEdit)
                    && location.getLocationName().equals(locationName.getText())) {
                errorLabel.setText("Location name already exists");
                errorLabel.setVisible(true);
                return;
            }
        }
        locationToEdit.setLocationName(locationName.getText());
        locationToEdit.setLocationAddress(locationAddress.getText());
        locationToEdit.setLocationCity(locationCity.getText());
        locationToEdit.setLocationState(locationState.getText());
        locationToEdit.setLocationZipCode(locationZipCode.getText());
        locationMapper.update(locationToEdit);
        stage.close();
    }
}