package graph.companygraphgenerator;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.Company;

public class CompanyBarGraphGenerator extends CompanyGraphGenerator {
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);

    public BarChart<String, Number> getBarGraph() {
        barChart.setTitle("Number of Times Worked For Each Company");
        barChart.setLegendVisible(false);
        xAxis.setLabel("Company");
        yAxis.setLabel("Number of Times Worked");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Populate the series with each company's name and
        // the number of times the user worked for each company
        Integer timesWorkedForCompany;
        for (Company company : companies) {
            timesWorkedForCompany = getNumberOfTimesWorkedForCompany(company.getCompanyName());
            series.getData().add(new XYChart.Data<>(company.getCompanyName(), timesWorkedForCompany));
        }

        barChart.getData().add(series);

        return barChart;
    }
}
