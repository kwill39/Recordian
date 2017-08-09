package com.kylewill.controller;

import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmap.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteSupervisorController implements Initializable{
    private MainViewController mainViewController;
    private Stage stage;
    private String displayNameOfSupervisorToDelete;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(event -> stage.close());
        deleteButton.setOnMouseClicked(event -> deleteSupervisor());
    }

    void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDisplayNameOfSupervisorToDelete(String displayNameOfSupervisorToDelete) {
        this.displayNameOfSupervisorToDelete = displayNameOfSupervisorToDelete;
    }

    private void deleteSupervisor() {
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
