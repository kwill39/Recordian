package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;

public class Controller {
    @FXML private TextField hours;
    @FXML private Button submit;

    // If the ENTER key was pressed after typing in the hours, submit a log
    @FXML private void keyCheck(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.ENTER) {
            submitHours();
        }
    }

    @FXML private void submitHours(){

        String hoursWorked = hours.getCharacters().toString();

        if (hoursWorked.isEmpty()) {
            //TODO: Add error dialog if the user hasn't input the hours before trying to submit a log
            return;
        }
        // Try appending to the current log file or create a new one if there is no log file
        try {
            FileWriter fileWriter = new FileWriter("Hours_Worked.txt", true);
            LocalDateTime localDateTime = LocalDateTime.now();
            int day = localDateTime.getDayOfMonth();
            Month month = localDateTime.getMonth();
            int year = localDateTime.getYear();
            String logEntry = System.lineSeparator() + month.toString() + " " + day + ", " + year + " - " + hoursWorked;
            fileWriter.append(logEntry);
            fileWriter.close();
        } catch (IOException exception) {
            System.err.println("Caught IOException: " + exception.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }
}
