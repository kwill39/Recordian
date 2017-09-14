package controller;

import databasemanagement.DatabaseChangeObservable;
import databasemanagement.DatabaseChangeObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import model.DatabaseItem;
import model.LogEntry;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class MainTabPaneController implements Initializable, DatabaseChangeObserver {
    private Stage currentStage;

    @FXML private Tab newLogTab;
    @FXML private Tab editLogEntriesTab;
    @FXML private Tab graphsTab;
    @FXML private Tab backupTab;

    @FXML private NewLogEntryTabController newLogEntryTabController;
    @FXML private SuccessfulLogSubmissionController successfulLogSubmissionController;
    @FXML private EditLogEntriesTabController editLogEntriesTabController;
    @FXML private GraphsTabController graphsTabController;
    @FXML private BackupTabController backupTabController;

    public MainTabPaneController() {
        DatabaseChangeObservable.registerObserver(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the initial tabs for the main tab pane
        FXMLLoader newLogEntryTabLoader = new FXMLLoader(getClass().getResource("/view/newLogEntryTab.fxml"));
        FXMLLoader editLogEntriesTabLoader = new FXMLLoader(getClass().getResource("/view/editLogEntriesTab.fxml"));
        FXMLLoader graphsTabLoader = new FXMLLoader(getClass().getResource("/view/graphsTab.fxml"));
        FXMLLoader backupTabLoader = new FXMLLoader(getClass().getResource("/view/backupTab.fxml"));
        try {
            newLogTab.setContent(newLogEntryTabLoader.load());
            editLogEntriesTab.setContent(editLogEntriesTabLoader.load());
            graphsTab.setContent(graphsTabLoader.load());
            backupTab.setContent(backupTabLoader.load());
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
        backupTabController = backupTabLoader.getController();
        backupTabController.setParentTabPaneController(this);

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
        backupTabController.setCurrentStage(currentStage);
    }

    /**
     * Performs certain actions that need to occur whenever a log entry
     * gets submitted by the user
     * <p>
     * This method is called whenever a log entry gets submitted by the user
     */
    void logEntryWasSubmitted() {
        showSuccessfulSubmissionView();
    }

    /**
     * Changes the current New Log tab view to the newLogEntryTab view
     * <p>
     * This method is called after the user acknowledges a
     * successful log entry submission
     */
    void userAcknowledgedSuccessfulLogEntrySubmission() {
        reloadNewLogEntryTab();
    }

    /**
     * Displays a confirmation view to the user informing them
     * that the log entry was successfully submitted to the database
     */
    private void showSuccessfulSubmissionView() {
        FXMLLoader successViewLoader = new FXMLLoader(getClass().getResource("/view/successfulLogSubmission.fxml"));
        try {
            newLogTab.setContent(successViewLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        successfulLogSubmissionController = successViewLoader.getController();
        successfulLogSubmissionController.setParentTabPaneController(this);
    }

    /**
     * Reloads the <code>newLogEntryTab</code> view
     */
    private void reloadNewLogEntryTab() {
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

    /**
     * Reloads the <code>editLogEntriesTab</code> view
     */
    private void reloadEditLogsTab() {
        FXMLLoader editLogEntriesTabLoader = new FXMLLoader(getClass().getResource("/view/editLogEntriesTab.fxml"));
        try {
            editLogEntriesTab.setContent(editLogEntriesTabLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        editLogEntriesTabController = editLogEntriesTabLoader.getController();
        editLogEntriesTabController.setParentTabPaneController(this);
        editLogEntriesTabController.setCurrentStage(currentStage);
    }

    /**
     * Reloads the <code>graphsTab</code> view
     */
    private void reloadGraphsTab() {
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

    @Override
    public void databaseItemWasCreated(DatabaseItem databaseItem) {
        if (databaseItem instanceof LogEntry) {
            reloadEditLogsTab();
        }
        reloadGraphsTab();
    }

    @Override
    public void databaseItemWasUpdated(DatabaseItem databaseItem) {
        reloadGraphsTab();
    }

    @Override
    public void databaseItemWasDeleted(DatabaseItem databaseItem) {
        reloadGraphsTab();
    }

    @Override
    public void databaseItemsWereDeleted(List<? extends DatabaseItem> databaseItems) {
        reloadGraphsTab();
    }
}
