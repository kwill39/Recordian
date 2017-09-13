package chart.piechart;

import chart.ChartFactory;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

abstract class PieChartFactory extends ChartFactory {

    public PieChart getPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle(chartTitle());
        pieChart.setData(pieChartData());
        return pieChart;
    }

    /**
     * Provides the data for the pie chart
     *
     * @return an {@link ObservableList} containing the data to be used in the pie chart
     */
    abstract ObservableList<PieChart.Data> pieChartData();
}
