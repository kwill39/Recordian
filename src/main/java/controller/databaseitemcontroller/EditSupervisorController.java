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
public class EditSupervisorController extends DatabaseItemModificationController implements Initializable {
    private SupervisorMapper supervisorMapper = new SupervisorMapper();
    private Supervisor supervisorToEdit;
    private String oldDisplayNameOfSupervisorToEdit;
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
        oldDisplayNameOfSupervisorToEdit = mainViewController.supervisorChoiceBox.getValue().toString();
        supervisorToEdit = supervisorMapper.read(oldDisplayNameOfSupervisorToEdit);
        supervisorFirstName.setText(supervisorToEdit.getSupervisorFirstName());
        supervisorLastName.setText(supervisorToEdit.getSupervisorLastName());
        supervisorDisplayName.setText(supervisorToEdit.getSupervisorDisplayName());
    }

    private void updateSupervisor() {
        if (supervisorFirstName.getText().isEmpty()
                || supervisorLastName.getText().isEmpty()
                || supervisorDisplayName.getText().isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            errorLabel.setVisible(true);
            return;
        }
        List<Supervisor> supervisors = supervisorMapper.readAll();
        for (Supervisor supervisor : supervisors) {
            if (!supervisor.getSupervisorDisplayName().equals(oldDisplayNameOfSupervisorToEdit)
                    && supervisor.getSupervisorDisplayName().equals(supervisorDisplayName.getText())) {
                errorLabel.setText("Supervisor \"Display As\" name already exists");
                errorLabel.setVisible(true);
                return;
            }
        }
        supervisorToEdit.setSupervisorFirstName(supervisorFirstName.getText());
        supervisorToEdit.setSupervisorLastName(supervisorLastName.getText());
        supervisorToEdit.setSupervisorDisplayName(supervisorDisplayName.getText());
        supervisorMapper.update(supervisorToEdit);
        stage.close();
    }
}