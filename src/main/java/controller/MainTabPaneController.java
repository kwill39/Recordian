package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainTabPaneController implements Initializable {
    private Stage currentStage;
    @FXML private Tab newLogTab;
    @FXML private Tab editLogEntriesTab;
    @FXML private Tab graphsTab;
    @FXML private NewLogEntryTabController newLogEntryTabController;
    @FXML private SuccessfulLogSubmissionController successfulLogSubmissionController;
    @FXML private EditLogEntriesTabController editLogEntriesTabController;
    @FXML private GraphsTabController graphsTabController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the initial tabs for the main tab pane
        FXMLLoader newLogEntryTabLoader = new FXMLLoader(getClass().getResource("/view/newLogEntryTab.fxml"));
        FXMLLoader editLogEntriesTabLoader = new FXMLLoader(getClass().getResource("/view/editLogEntriesTab.fxml"));
        FXMLLoader graphsTabLoader = new FXMLLoader(getClass().getResource("/view/graphsTab.fxml"));
        try {
            newLogTab.setContent(newLogEntryTabLoader.load());
            editLogEntriesTab.setContent(editLogEntriesTabLoader.load());
            graphsTab.setContent(graphsTabLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // The tabs will need to know their parent
        // tab pane in order to communicate back to it
        newLogEntryTabController = newLogEntryTabLoader.getController();
        newLogEntryTabController.setParentTabPaneController(this);
        editLogEntriesTabController = editLogEntriesTabLoader.getController();
        editLogEntriesTabController.setParentTabPaneController(this);
        graphsTabController = graphsTabLoader.getController();
        graphsTabController.setParentTabPaneController(this);

    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage stage) {
        currentStage = stage;
        // Some of the tabs may need to know their stage
        newLogEntryTabController.setCurrentStage(currentStage);
        editLogEntriesTabController.setCurrentStage(currentStage);
        graphsTabController.setCurrentStage(currentStage);
    }

    /**
     * Performs certain actions that need to occur whenever a log entry
     * gets submitted by the user
     * <p>
     * This method should be called whenever a log entry gets submitted by the user.
     */
    void logEntryWasSubmitted() {
        // Show the successful submission view
        FXMLLoader successViewLoader = new FXMLLoader(getClass().getResource("/view/successfulLogSubmission.fxml"));
        try {
            newLogTab.setContent(successViewLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        successfulLogSubmissionController = successViewLoader.getController();
        successfulLogSubmissionController.setParentTabPaneController(this);

        // Reset the Edit Logs tab so that it displays the most recent submission
        FXMLLoader editLogEntriesTabLoader = new FXMLLoader(getClass().getResource("/view/editLogEntriesTab.fxml"));
        try {
            editLogEntriesTab.setContent(editLogEntriesTabLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        editLogEntriesTabController = editLogEntriesTabLoader.getController();
        editLogEntriesTabController.setParentTabPaneController(this);
        editLogEntriesTabController.setCurrentStage(currentStage);

        // Reset the Graphs tab so that it takes into account the most recent submission
        FXMLLoader graphsTabLoader = new FXMLLoader(getClass().getResource("/view/graphsTab.fxml"));
        try {
            graphsTab.setContent(graphsTabLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphsTabController = graphsTabLoader.getController();
        graphsTabController.setParentTabPaneController(this);
        graphsTabController.setCurrentStage(currentStage);

    }

    /**
     * Changes the current New Log tab view to the newLogEntryTab view
     * <p>
     * This method should be called after the user acknowledges a
     * successful log entry submission
     */
    void userAcknowledgedSuccessfulLogEntrySubmission() {
        FXMLLoader newLogViewLoader = new FXMLLoader(getClass().getResource("/view/newLogEntryTab.fxml"));
        try {
            newLogTab.setContent(newLogViewLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        newLogEntryTabController = newLogViewLoader.getController();
        newLogEntryTabController.setCurrentStage(currentStage);
        newLogEntryTabController.setParentTabPaneController(this);
    }
}
