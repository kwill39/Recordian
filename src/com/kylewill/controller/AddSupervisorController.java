package com.kylewill.controller;

import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmaps.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupervisorController implements Initializable{
    private MainViewController mainViewController;
    private Stage stage;
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

    void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    private void addSupervisor() {
        Supervisor newSupervisor = new Supervisor(newSupervisorDisplayName.getText());
        newSupervisor.setSupervisorFirstName(newSupervisorFirstName.getText());
        newSupervisor.setSupervisorLastName(newSupervisorLastName.getText());
        SupervisorMapper.create(newSupervisor);
        mainViewController.refreshSupervisorDisplayNames();
        stage.close();
    }
}
