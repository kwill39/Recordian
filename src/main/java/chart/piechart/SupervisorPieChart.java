package chart.piechart;

import chart.ChartDataQuery;
import databasemanagement.objectrelationalmap.SupervisorMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import model.Supervisor;

import java.util.List;

public class SupervisorPieChart extends PieChartFactory {
    private SupervisorMapper supervisorMapper = new SupervisorMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    protected String sqlQuery() {
        return ChartDataQuery.SUPERVISOR.getSqlQuery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String chartTitle() {
        return "Work Frequency Under Each Supervisor";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    ObservableList<PieChart.Data> pieChartData() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        List<Supervisor> supervisors = supervisorMapper.readAll();
        Integer timesWorkedUnderSupervisor;
        for (Supervisor supervisor : supervisors) {
            timesWorkedUnderSupervisor =
                    getNumberOfLogEntriesContainingSpecifiedDatabaseItem(supervisor.getSupervisorDisplayName());
            data.add(new PieChart.Data(supervisor.getSupervisorDisplayName(), timesWorkedUnderSupervisor));
        }
        return data;
    }
}
