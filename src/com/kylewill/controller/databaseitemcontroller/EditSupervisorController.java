package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmap.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditSupervisorController extends DatabaseItemController implements Initializable {
    private Supervisor supervisorToEdit;
    private @FXML Button saveButton;
    private @FXML Button cancelButton;
    private @FXML TextField supervisorFirstName;
    private @FXML TextField supervisorLastName;
    private @FXML TextField supervisorDisplayName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateSupervisor());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    @Override
    protected void onMainViewControllerSet() {
        String displayNameOfSupervisorToEdit = mainViewController.supervisorChoiceBox.getValue().toString();
        List<Supervisor> supervisors = SupervisorMapper.readAll();
        for (Supervisor someSupervisor : supervisors) {
            if (someSupervisor.getSupervisorDisplayName().equals(displayNameOfSupervisorToEdit)){
                supervisorToEdit = someSupervisor;
            }
        }
        supervisorFirstName.setText(supervisorToEdit.getSupervisorFirstName());
        supervisorLastName.setText(supervisorToEdit.getSupervisorLastName());
        supervisorDisplayName.setText(supervisorToEdit.getSupervisorDisplayName());
    }

    private void updateSupervisor() {
        supervisorToEdit.setSupervisorFirstName(supervisorFirstName.getText());
        supervisorToEdit.setSupervisorLastName(supervisorLastName.getText());
        supervisorToEdit.setSupervisorDisplayName(supervisorDisplayName.getText());
        SupervisorMapper.update(supervisorToEdit);
        mainViewController.refreshSupervisorDisplayNames();
        stage.close();
    }
}
