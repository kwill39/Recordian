package controller;

import chart.barchart.CompanyBarChart;
import chart.piechart.CompanyPieChart;
import chart.piechart.LocationPieChart;
import chart.piechart.SupervisorPieChart;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
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
    private static final String BAR_CHART = "BAR_CHART";
    private static final String PIE_CHART = "PIE_CHART";
    private BarChart<String, Number> companyBarChart = new CompanyBarChart().getBarChart();
    private PieChart locationPieChart = new LocationPieChart().getPieChart();
    private PieChart companyPieChart = new CompanyPieChart().getPieChart();
    private PieChart supervisorPieChart = new SupervisorPieChart().getPieChart();
    @FXML private ToggleGroup chartType;
    @FXML private JFXRadioButton barChartButton;
    @FXML private JFXRadioButton pieChartButton;
    @FXML private ScrollPane chartScrollPane;
    @FXML private VBox chartContainer;
    @FXML private JFXButton generateReportButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    void setParentTabPaneController(MainTabPaneController parentTabPane) {
        this.parentTabPaneController = parentTabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set default chart view
        chartContainer.getChildren().addAll(companyBarChart);

        // In order to center the charts to the scroll pane,
        // the chartContainer (VBox) needs to have the same width as its scroll pane
        chartContainer.minWidthProperty().bind(chartScrollPane.widthProperty());

        // Used to find out which radio button was selected
        barChartButton.setUserData(BAR_CHART);
        pieChartButton.setUserData(PIE_CHART);

        // Detect which radio button was selected and
        // add its corresponding charts to the chart container
        chartType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getUserData().toString()) {
                case BAR_CHART:
                    chartContainer.getChildren().clear();
                    chartContainer.getChildren().addAll(companyBarChart);
                    break;
                case PIE_CHART:
                    chartContainer.getChildren().clear();
                    chartContainer.getChildren().addAll(locationPieChart, companyPieChart, supervisorPieChart);
                    break;
            }
        });
    }
}
