package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmap.SupervisorMapper;
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
public class EditSupervisorController extends DatabaseItemModificationController implements Initializable {
    private SupervisorMapper supervisorMapper = new SupervisorMapper();
    private Supervisor supervisorToEdit;
    @FXML private TextField supervisorFirstName;
    @FXML private TextField supervisorLastName;
    @FXML private TextField supervisorDisplayName;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateSupervisor());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    @Override
    protected void onMainViewControllerSet() {
        String displayNameOfSupervisorToEdit = mainViewController.supervisorChoiceBox.getValue().toString();
        supervisorToEdit = supervisorMapper.read(displayNameOfSupervisorToEdit);
        supervisorFirstName.setText(supervisorToEdit.getSupervisorFirstName());
        supervisorLastName.setText(supervisorToEdit.getSupervisorLastName());
        supervisorDisplayName.setText(supervisorToEdit.getSupervisorDisplayName());
    }

    private void updateSupervisor() {
        if (supervisorFirstName.getText().isEmpty()
                || supervisorLastName.getText().isEmpty()
                || supervisorDisplayName.getText().isEmpty()) {
            errorLabel.setVisible(true);
        } else {
            supervisorToEdit.setSupervisorFirstName(supervisorFirstName.getText());
            supervisorToEdit.setSupervisorLastName(supervisorLastName.getText());
            supervisorToEdit.setSupervisorDisplayName(supervisorDisplayName.getText());
            supervisorMapper.update(supervisorToEdit);
            mainViewController.refreshSupervisorDisplayNames();
            stage.close();
        }
    }
}
