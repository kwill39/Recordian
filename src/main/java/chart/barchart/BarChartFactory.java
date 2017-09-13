package chart.barchart;

import chart.ChartFactory;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

abstract class BarChartFactory extends ChartFactory {
    protected final CategoryAxis xAxis = new CategoryAxis();
    protected final NumberAxis yAxis = new NumberAxis();

    public BarChart<String, Number> getBarChart() {
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(chartTitle());
        barChart.setLegendVisible(false);
        yAxis.setLabel(barChartYAxisLabel());
        xAxis.setLabel(barChartXAxisLabel());
        barChart.getData().add(barChartData());
        return barChart;
    }

    /**
     * Generates the bar chart's y-axis label
     */
    protected String barChartYAxisLabel() {
        return "Number of Times Worked";
    }

    /**
     * Generates the bar chart's x-axis label
     */
    abstract String barChartXAxisLabel();
    /**
     * Provides the data for the bar chart
     *
     * @return an {@link XYChart.Series} containing the data to be used in the bar chart
     */
    abstract XYChart.Series<String, Number> barChartData();
}
