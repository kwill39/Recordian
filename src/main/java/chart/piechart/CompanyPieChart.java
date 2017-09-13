package chart.piechart;

import chart.ChartDataQuery;
import databasemanagement.objectrelationalmap.CompanyMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import model.Company;

import java.util.List;

public class CompanyPieChart extends PieChartFactory {
    private CompanyMapper companyMapper = new CompanyMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    protected String sqlQuery() {
        return ChartDataQuery.COMPANY.getSqlQuery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String chartTitle() {
        return "Work Frequency For Each Company";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    ObservableList<PieChart.Data> pieChartData() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        List<Company> companies = companyMapper.readAll();
        Integer timesWorkedForCompany;
        for (Company company : companies) {
            timesWorkedForCompany = getNumberOfLogEntriesContainingSpecifiedDatabaseItem(company.getCompanyName());
            data.add(new PieChart.Data(company.getCompanyName(), timesWorkedForCompany));
        }
        return data;
    }
}
