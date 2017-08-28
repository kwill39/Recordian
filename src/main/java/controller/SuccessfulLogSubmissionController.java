package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class SuccessfulLogSubmissionController implements Initializable {
    private MainTabPaneController parentTabPaneController;
    @FXML private Button newLogButton;

    public void setParentTabPaneController(MainTabPaneController parentTabPaneController) {
        this.parentTabPaneController = parentTabPaneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newLogButton.setOnMouseClicked(event -> parentTabPaneController.userAcknowledgedSuccessfulLogEntrySubmission());
    }
}
