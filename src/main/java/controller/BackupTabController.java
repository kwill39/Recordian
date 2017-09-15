package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import databasemanagement.DatabaseHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class BackupTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    @FXML private StackPane tabRootPane;
    @FXML private JFXButton createBackupButton;
    @FXML private JFXButton importBackupButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    void setParentTabPaneController(MainTabPaneController parentTabPane) {
        this.parentTabPaneController = parentTabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createBackupButton.setOnMouseClicked(event -> generateBackupCopy());
        importBackupButton.setOnMouseClicked(event -> importBackup());
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

    /**
     * Imports a database with a structure that is exactly like
     * that of the structure of a database used within this application
     * and replaces the currently used database with the imported database
     */
    private void importBackup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Backup Copy");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        FileChooser.ExtensionFilter databaseExtensionFilter =
                new FileChooser.ExtensionFilter(
                        "DB - Database (.db)", "*.db");
        fileChooser.getExtensionFilters().add(databaseExtensionFilter);
        fileChooser.setSelectedExtensionFilter(databaseExtensionFilter);
        File backupFile = fileChooser.showOpenDialog(currentStage);

        if (backupFile != null) {
            try {
                // Import the database copy
                File theDatabase = new File(DatabaseHelper.DATABASE_PATH_NAME);
                Files.copy(backupFile.toPath(), theDatabase.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Inform the parent TabPane controller that a new database was imported
                parentTabPaneController.newDatabaseWasImported();

                // Inform the user that the import was successful
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                Text headingText = new Text("Success");
                headingText.setFill(Paint.valueOf("#6c93e4"));
                dialogLayout.setHeading(headingText);
                Text body = new Text("The backup was successfully imported.");
                dialogLayout.setBody(body);

                JFXDialog dialog = new JFXDialog(tabRootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

                JFXButton okButton = new JFXButton("OKAY");
                okButton.setOnMouseClicked(deleteEvent -> {
                    dialog.close();
                });
                okButton.setTextFill(Color.valueOf("#6c93e4"));
                okButton.getStyleClass().add("dialogButton");

                dialogLayout.setActions(okButton);

                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}