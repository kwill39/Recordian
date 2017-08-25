package controller.databaseitemcontroller;

import databasemanagement.objectrelationalmap.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Supervisor;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class DeleteSupervisorController extends DatabaseItemModificationController implements Initializable{
    private SupervisorMapper supervisorMapper = new SupervisorMapper();
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
    protected void onNewLogEntryTabControllerSet() {
        String displayNameOfSupervisorToDelete = newLogEntryTabController.supervisorChoiceBox.getValue().toString();
        supervisorToDelete = supervisorMapper.read(displayNameOfSupervisorToDelete);
        confirmationLabel.setText("Are you sure you want to delete " + supervisorToDelete.getSupervisorDisplayName() + "?");
    }

    private void deleteSupervisor() {
        supervisorMapper.delete(supervisorToDelete);
        stage.close();
    }
}