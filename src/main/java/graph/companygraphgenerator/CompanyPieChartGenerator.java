package graph.companygraphgenerator;

import databasemanagement.objectrelationalmap.CompanyMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import model.Company;

import java.util.List;

public class CompanyPieChartGenerator extends CompanyGraphGenerator {

    public PieChart getPieChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        CompanyMapper companyMapper = new CompanyMapper();
        List<Company> companies = companyMapper.readAll();
        Integer timesWorkedForCompany;
        for (Company company : companies) {
            timesWorkedForCompany = getNumberOfTimesWorkedForCompany(company.getCompanyName());
            data.add(new PieChart.Data(company.getCompanyName(), timesWorkedForCompany));
        }
        PieChart companysWorkedFor = new PieChart(data);
        companysWorkedFor.setTitle("Work Frequency For Each Company");
        return companysWorkedFor;
    }
}
