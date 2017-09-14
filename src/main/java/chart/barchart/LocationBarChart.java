package chart.barchart;

import chart.ChartDataQuery;
import databasemanagement.objectrelationalmap.LocationMapper;
import javafx.scene.chart.XYChart;
import model.Location;

import java.util.List;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class LocationBarChart extends BarChartFactory {
    private LocationMapper locationMapper = new LocationMapper();

    @Override
    String barChartXAxisLabel() {
        return "Location";
    }

    @Override
    XYChart.Series<String, Number> barChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        List<Location> locations = locationMapper.readAll();
        Integer timesWorkedAtLocation;
        for (Location location : locations) {
            timesWorkedAtLocation = getNumberOfLogEntriesContainingSpecifiedDatabaseItem(location.getLocationName());
            series.getData().add(new XYChart.Data<>(location.getLocationName(), timesWorkedAtLocation));
        }
        return series;
    }

    @Override
    protected String sqlQuery() {
        return ChartDataQuery.LOCATION.getSqlQuery();
    }

    @Override
    protected String chartTitle() {
        return "Number of Times Worked At Each Location";
    }
}
