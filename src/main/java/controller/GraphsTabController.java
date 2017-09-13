package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import graph.companygraphgenerator.CompanyPieChartGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphsTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    private PieChart companyPieChart = new CompanyPieChartGenerator().getPieChart();
    @FXML private ToggleGroup graphType;
    @FXML private JFXRadioButton barGraphButton;
    @FXML private JFXRadioButton pieChartButton;
    @FXML private ScrollPane graphScrollPane;
    @FXML private VBox graphContainer;
    @FXML private JFXButton generateReportButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    void setParentTabPaneController(MainTabPaneController parentTabPane) {
        this.parentTabPaneController = parentTabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphContainer.prefWidthProperty().bind(graphScrollPane.widthProperty());
        graphContainer.prefHeightProperty().bind(graphScrollPane.heightProperty());
        barGraphButton.setUserData("barGraph");
        pieChartButton.setUserData("pieChart");
        graphType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getUserData().toString()) {
                case "barGraph": break;
                case "pieChart":
                    graphContainer.getChildren().clear();
                    graphContainer.getChildren().add(companyPieChart);
                    break;
            }
        });
    }
}
