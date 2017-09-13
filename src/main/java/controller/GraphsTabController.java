package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import graph.companygraphgenerator.CompanyPieChartGenerator;
import graph.locationgraphgenerator.LocationPieChartGenerator;
import graph.supervisorgraphgenerator.SupervisorPieChartGenerator;
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
    private PieChart locationPieChart = new LocationPieChartGenerator().getPieChart();
    private PieChart companyPieChart = new CompanyPieChartGenerator().getPieChart();
    private PieChart supervisorPieChart = new SupervisorPieChartGenerator().getPieChart();
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

        // In order to center the charts to the scroll pane,
        // the graphContainer (VBox) needs to have the same width as its scroll pane
        graphContainer.prefWidthProperty().bind(graphScrollPane.widthProperty());

        // Used to find out which radio button was selected
        barGraphButton.setUserData("barGraph");
        pieChartButton.setUserData("pieChart");

        // Detect which radio button was selected and
        // add its corresponding graphs to the graph container
        graphType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getUserData().toString()) {
                case "barGraph": break;
                case "pieChart":
                    graphContainer.getChildren().clear();
                    graphContainer.getChildren().addAll(locationPieChart, companyPieChart, supervisorPieChart);
                    break;
            }
        });
    }
}
