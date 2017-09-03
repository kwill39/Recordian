package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class SuccessfulLogSubmissionController implements Initializable {
    private MainTabPaneController parentTabPaneController;
    @FXML private JFXButton newLogButton;

    public void setParentTabPaneController(MainTabPaneController parentTabPaneController) {
        this.parentTabPaneController = parentTabPaneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> newLogButton.requestFocus());
        newLogButton.setOnMouseClicked(event -> parentTabPaneController.userAcknowledgedSuccessfulLogEntrySubmission());
        newLogButton.setOnKeyReleased(keyReleased -> {
            if (keyReleased.getCode() == KeyCode.ENTER) {
                parentTabPaneController.userAcknowledgedSuccessfulLogEntrySubmission();
            }
        });
    }
}
