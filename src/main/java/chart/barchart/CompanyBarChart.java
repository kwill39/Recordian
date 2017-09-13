package chart.barchart;

import chart.ChartDataQuery;
import databasemanagement.objectrelationalmap.CompanyMapper;
import javafx.scene.chart.XYChart;
import model.Company;

import java.util.List;

public class CompanyBarChart extends BarChartFactory {
    private CompanyMapper companyMapper = new CompanyMapper();

    @Override
    String barChartXAxisLabel() {
        return "Company";
    }

    @Override
    XYChart.Series<String, Number> barChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        List<Company> companies = companyMapper.readAll();
        Integer timesWorkedForCompany;
        for (Company company : companies) {
            timesWorkedForCompany = getNumberOfLogEntriesContainingSpecifiedDatabaseItem(company.getCompanyName());
            series.getData().add(new XYChart.Data<>(company.getCompanyName(), timesWorkedForCompany));
        }
        return series;
    }

    @Override
    protected String sqlQuery() {
        return ChartDataQuery.COMPANY.getSqlQuery();
    }

    @Override
    protected String chartTitle() {
        return "Number of Times Worked For Each Company";
    }
}
