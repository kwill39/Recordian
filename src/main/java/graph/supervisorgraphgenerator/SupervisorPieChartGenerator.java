package graph.supervisorgraphgenerator;

import databasemanagement.objectrelationalmap.SupervisorMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import model.Supervisor;

import java.util.List;

public class SupervisorPieChartGenerator extends SupervisorGraphGenerator {

    public PieChart getPieChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        SupervisorMapper supervisorMapper = new SupervisorMapper();
        List<Supervisor> supervisors = supervisorMapper.readAll();
        Integer timesWorkedUnderSupervisor;
        for (Supervisor supervisor : supervisors) {
            timesWorkedUnderSupervisor = getNumberOfTimesWorkedUnderSupervisor(supervisor.getSupervisorDisplayName());
            data.add(new PieChart.Data(supervisor.getSupervisorDisplayName(), timesWorkedUnderSupervisor));
        }
        PieChart supervisorsWorkedUnder = new PieChart(data);
        supervisorsWorkedUnder.setTitle("Work Frequency Under Each Supervisor");
        return supervisorsWorkedUnder;
    }
}
