package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Supervisor;
import com.kylewill.databasemanagement.objectrelationalmap.SupervisorMapper;
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
public class AddSupervisorController extends DatabaseItemModificationController implements Initializable{
    private SupervisorMapper supervisorMapper = new SupervisorMapper();
    @FXML private TextField newSupervisorFirstName;
    @FXML private TextField newSupervisorLastName;
    @FXML private TextField newSupervisorDisplayName;
    @FXML private Label errorLabel;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(event -> addSupervisor());
        cancelButton.setOnMouseClicked(event -> stage.close());
        newSupervisorFirstName.setOnKeyReleased(event -> {
            String displayNameValue = newSupervisorFirstName.getText();
            if (!newSupervisorLastName.getText().isEmpty()) {
                displayNameValue = newSupervisorFirstName.getText() + " "  + newSupervisorLastName.getText();
            }
            newSupervisorDisplayName.setText(displayNameValue.trim());
        });
        newSupervisorLastName.setOnKeyReleased(event -> {
            String displayNameValue = newSupervisorLastName.getText();
            if (!newSupervisorFirstName.getText().isEmpty()) {
                displayNameValue = newSupervisorFirstName.getText() + " "  + newSupervisorLastName.getText();
            }
            newSupervisorDisplayName.setText(displayNameValue.trim());
        });
    }

    private void addSupervisor() {
        if (newSupervisorFirstName.getText().isEmpty()
                || newSupervisorLastName.getText().isEmpty()
                || newSupervisorDisplayName.getText().isEmpty()) {
            errorLabel.setVisible(true);
        } else {
            Supervisor newSupervisor = new Supervisor(newSupervisorDisplayName.getText());
            newSupervisor.setSupervisorFirstName(newSupervisorFirstName.getText());
            newSupervisor.setSupervisorLastName(newSupervisorLastName.getText());
            supervisorMapper.create(newSupervisor);
            stage.close();
        }
    }
}
