package controller;

import com.jfoenix.controls.JFXButton;
import databasemanagement.DatabaseHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BackupTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    @FXML private JFXButton backupButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    void setParentTabPaneController(MainTabPaneController parentTabPane) {
        this.parentTabPaneController = parentTabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backupButton.setOnMouseClicked(event -> generateBackupCopy());
    }

    /**
     * Generates a copy of the database and lets
     * the user choose where to save it
     */
    private void generateBackupCopy() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Backup Copy");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("Backup Copy " + LocalDate.now() + ".db");
        FileChooser.ExtensionFilter databaseExtensionFilter =
                new FileChooser.ExtensionFilter(
                        "DB - Database (.db)", "*.db");
        fileChooser.getExtensionFilters().add(databaseExtensionFilter);
        fileChooser.setSelectedExtensionFilter(databaseExtensionFilter);
        File backupFile = fileChooser.showSaveDialog(currentStage);

        if (backupFile != null) {
            try {
                File theDatabase = new File(DatabaseHelper.DATABASE_PATH_NAME);
                if (!theDatabase.exists()) {
                    DatabaseHelper.createDatabase();
                }
                Files.copy(theDatabase.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}