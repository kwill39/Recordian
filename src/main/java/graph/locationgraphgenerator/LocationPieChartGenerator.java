package graph.locationgraphgenerator;

import databasemanagement.objectrelationalmap.LocationMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import model.Location;

import java.util.List;

public class LocationPieChartGenerator extends LocationGraphGenerator {

    public PieChart getPieChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        LocationMapper locationMapper = new LocationMapper();
        List<Location> locations = locationMapper.readAll();
        Integer timesWorkedAtLocation;
        for (Location location : locations) {
            timesWorkedAtLocation = getNumberOfTimesWorkedAtLocation(location.getLocationName());
            data.add(new PieChart.Data(location.getLocationName(), timesWorkedAtLocation));
        }
        PieChart locationsWorkedAt = new PieChart(data);
        locationsWorkedAt.setTitle("Work Frequency At Each Location");
        return locationsWorkedAt;
    }
}
