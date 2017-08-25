package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EditReportTabController implements Initializable {
    private Stage currentStage;
    private String logFilePath = "Hours_Worked.txt";
    @FXML private TextArea editLogFileTextArea;
    @FXML private Button generateReportButton;
    @FXML private Button saveButton;
    @FXML private Button undoChangesButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Keep the save and cancel buttons disabled if the contents of
        // editLogFileTextArea matches the contents of the log file
        // Disable the buttons initially since no changes were made
        saveButton.setDisable(true);
        undoChangesButton.setDisable(true);

        saveButton.setOnMouseClicked(event -> {
            saveNewLogFileText();
            saveButton.setDisable(true);
            undoChangesButton.setDisable(true);
            generateReportButton.setDisable(false);
        });

        editLogFileTextArea.setText(getLogFileText());
        // When changes are made, enable the options to save or
        // cancel those changes and disable the option to generate
        // a report until the changes are saved or canceled.
        editLogFileTextArea.setOnKeyPressed(event -> {
            saveButton.setDisable(false);
            undoChangesButton.setDisable(false);
            generateReportButton.setDisable(true);
        });

        // Onclick: reset editLogFileTextArea to match
        // the contents of the log file, disable the save button
        undoChangesButton.setOnMouseClicked(event -> {
            saveButton.setDisable(true);
            undoChangesButton.setDisable(true);
            editLogFileTextArea.setText(getLogFileText());
            generateReportButton.setDisable(false);
        });

        generateReportButton.setOnMouseClicked(event -> generateReport());
    }

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    /**
     * Overwrites the log file with the contents of editLogFileTextArea
     */
    private void saveNewLogFileText() {
        String newLogFileText = editLogFileTextArea.getText();
        try (FileWriter fileWriter = new FileWriter(logFilePath)) {
            fileWriter.write(newLogFileText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads in the text from the log file
     *
     * @return String representing the text in the log file
     */
    private String getLogFileText() {
        File logFile = new File(logFilePath);
        try {
            // Create a new log file if one does not currently exist
            logFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (
                FileInputStream fileInputStream = new FileInputStream(logFilePath);
                Scanner scanner = new Scanner(fileInputStream)
        ) {
            StringBuilder logFileText = new StringBuilder();
            while (scanner.hasNextLine()) {
                logFileText.append(scanner.nextLine());
                // Add the newline which scanner removed
                logFileText.append(System.lineSeparator());
            }
            return logFileText.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void generateReport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Copy of Report");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("Hour Tracker Report.txt");
        File file = fileChooser.showSaveDialog(currentStage);
        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(getLogFileText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
