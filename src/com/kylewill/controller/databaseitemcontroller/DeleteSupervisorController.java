package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmap.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteSupervisorController extends DatabaseItemController implements Initializable{
    private Supervisor supervisorToDelete;
    @FXML private Label confirmationLabel;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(event -> stage.close());
        deleteButton.setOnMouseClicked(event -> deleteSupervisor());
    }

    @Override
    protected void onMainViewControllerSet() {
        String displayNameOfSupervisorToDelete = mainViewController.supervisorChoiceBox.getValue().toString();
        List<Supervisor> supervisors = SupervisorMapper.readAll();
        for (Supervisor someSupervisor : supervisors){
            if (someSupervisor.getSupervisorDisplayName().equals(displayNameOfSupervisorToDelete)){
                supervisorToDelete = someSupervisor;
            }
        }
        confirmationLabel.setText("Are you sure you want to delete " + supervisorToDelete.getSupervisorDisplayName() + "?");
    }

    private void deleteSupervisor() {
        SupervisorMapper.delete(supervisorToDelete);
        mainViewController.refreshSupervisorDisplayNames();
        stage.close();
    }
}
