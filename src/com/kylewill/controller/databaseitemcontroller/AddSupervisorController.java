package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.controller.MainViewController;
import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmap.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupervisorController extends DatabaseItemController implements Initializable{
    @FXML private TextField newSupervisorFirstName;
    @FXML private TextField newSupervisorLastName;
    @FXML private TextField newSupervisorDisplayName;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    public AddSupervisorController(Stage stage, MainViewController mainViewController) {
        super(stage, mainViewController);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(event -> addSupervisor());
        cancelButton.setOnMouseClicked(event -> stage.close());
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
