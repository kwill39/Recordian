package chart.barchart;

import chart.ChartDataQuery;
import databasemanagement.objectrelationalmap.SupervisorMapper;
import javafx.scene.chart.XYChart;
import model.Supervisor;

import java.util.List;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class SupervisorBarChart extends BarChartFactory {
    private SupervisorMapper supervisorMapper = new SupervisorMapper();

    @Override
    String barChartXAxisLabel() {
        return "Supervisor";
    }

    @Override
    XYChart.Series<String, Number> barChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        List<Supervisor> supervisors = supervisorMapper.readAll();
        Integer timesWorkedUnderSupervisor;
        for (Supervisor supervisor : supervisors) {
            timesWorkedUnderSupervisor = getNumberOfLogEntriesContainingSpecifiedDatabaseItem(supervisor.getSupervisorDisplayName());
            series.getData().add(new XYChart.Data<>(supervisor.getSupervisorDisplayName(), timesWorkedUnderSupervisor));
        }
        return series;
    }

    @Override
    protected String sqlQuery() {
        return ChartDataQuery.SUPERVISOR.getSqlQuery();
    }

    @Override
    protected String chartTitle() {
        return "Number of Times Worked Under Each Supervisor";
    }
}
