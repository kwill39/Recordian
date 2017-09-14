package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class ErrorController implements Initializable {
    private Stage stage;
    @FXML private Label errorLabel;
    @FXML private Button okButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        okButton.setOnMouseClicked(event -> stage.close());
    }

    /**
     * @param errorMessage a String that gets displayed to
     *                     the user through errorLabel
     */
    public void setCustomErrorMessage(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    /**
     * @param stage the JavaFX {@link Stage} that contains the
     *              view which this controller interacts with
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}