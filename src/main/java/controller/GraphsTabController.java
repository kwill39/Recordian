package controller;

import chart.barchart.CompanyBarChart;
import chart.barchart.LocationBarChart;
import chart.barchart.SupervisorBarChart;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GraphsTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    private static final String BAR_CHART = "BAR_CHART";
    private static final String PIE_CHART = "PIE_CHART";
    private List<BarChart<String, Number>> barCharts =
            new ArrayList<>(Arrays.asList(
                    new LocationBarChart().getBarChart(),
                    new CompanyBarChart().getBarChart(),
                    new SupervisorBarChart().getBarChart()
            ));
    private List<PieChart> pieCharts =
            new ArrayList<>(Arrays.asList(
                    new LocationPieChart().getPieChart(),
                    new CompanyPieChart().getPieChart(),
                    new SupervisorPieChart().getPieChart()
            ));
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
        chartContainer.getChildren().addAll(barCharts);

        chartScrollPane.setFitToWidth(true);

        // Used to find out which radio button was selected
        barChartButton.setUserData(BAR_CHART);
        pieChartButton.setUserData(PIE_CHART);

        // Detect which radio button was selected and
        // add its corresponding charts to the chart container
        chartType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getUserData().toString()) {
                case BAR_CHART:
                    chartContainer.getChildren().clear();
                    chartContainer.getChildren().addAll(barCharts);
                    break;
                case PIE_CHART:
                    chartContainer.getChildren().clear();
                    chartContainer.getChildren().addAll(pieCharts);
                    break;
            }
        });
    }
}
