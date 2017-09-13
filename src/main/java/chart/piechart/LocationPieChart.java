package chart.piechart;

import chart.ChartDataQuery;
import databasemanagement.objectrelationalmap.LocationMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import model.Location;

import java.util.List;

public class LocationPieChart extends PieChartFactory {
    private LocationMapper locationMapper = new LocationMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    protected String sqlQuery() {
        return ChartDataQuery.LOCATION.getSqlQuery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String chartTitle() {
        return "Work Frequency At Each Location";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    ObservableList<PieChart.Data> pieChartData() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        List<Location> locations = locationMapper.readAll();
        Integer timesWorkedAtLocation;
        for (Location location : locations) {
            timesWorkedAtLocation = getNumberOfLogEntriesContainingSpecifiedDatabaseItem(location.getLocationName());
            data.add(new PieChart.Data(location.getLocationName(), timesWorkedAtLocation));
        }
        return data;
    }
}
