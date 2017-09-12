package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphsTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    @FXML private ScrollPane graphContainer;
    @FXML private ToggleGroup graphType;
    @FXML private JFXButton generateReportButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    void setParentTabPaneController(MainTabPaneController parentTabPane) {
        this.parentTabPaneController = parentTabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
