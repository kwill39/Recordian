package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.controller.MainViewController;
import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmap.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteSupervisorController extends DatabaseItemController implements Initializable{
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(event -> stage.close());
        deleteButton.setOnMouseClicked(event -> deleteSupervisor());
    }

    private void deleteSupervisor() {
        String displayNameOfSupervisorToDelete = mainViewController.supervisorChoiceBox.getValue().toString();
        List<Supervisor> supervisors = SupervisorMapper.readAll();
        for (Supervisor someSupervisor : supervisors){
            if (someSupervisor.getSupervisorDisplayName().equals(displayNameOfSupervisorToDelete)){
                SupervisorMapper.delete(someSupervisor);
            }
        }
        mainViewController.refreshSupervisorDisplayNames();
        stage.close();
    }
}
