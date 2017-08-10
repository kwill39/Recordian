package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmap.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupervisorController extends DatabaseItemController implements Initializable{
    private SupervisorMapper supervisorMapper = new SupervisorMapper();
    @FXML private TextField newSupervisorFirstName;
    @FXML private TextField newSupervisorLastName;
    @FXML private TextField newSupervisorDisplayName;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(event -> addSupervisor());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    private void addSupervisor() {
        Supervisor newSupervisor = new Supervisor(newSupervisorDisplayName.getText());
        newSupervisor.setSupervisorFirstName(newSupervisorFirstName.getText());
        newSupervisor.setSupervisorLastName(newSupervisorLastName.getText());
        supervisorMapper.create(newSupervisor);
        mainViewController.refreshSupervisorDisplayNames();
        stage.close();
    }
}
