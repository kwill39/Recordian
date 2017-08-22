package controller.databaseitemcontroller;

import databasemanagement.objectrelationalmap.SupervisorMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Supervisor;

import java.net.URL;
import java.util.List;
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
            errorLabel.setText("Please fill in all fields");
            errorLabel.setVisible(true);
            return;
        }
        List<Supervisor> supervisors = supervisorMapper.readAll();
        for (Supervisor supervisor : supervisors) {
            if (supervisor.getSupervisorDisplayName().equals(newSupervisorDisplayName.getText())) {
                errorLabel.setText("Supervisor \"Display As\" name already exists");
                errorLabel.setVisible(true);
                return;
            }
        }
        Supervisor newSupervisor = new Supervisor(newSupervisorDisplayName.getText());
        newSupervisor.setSupervisorFirstName(newSupervisorFirstName.getText());
        newSupervisor.setSupervisorLastName(newSupervisorLastName.getText());
        supervisorMapper.create(newSupervisor);
        stage.close();
    }
}