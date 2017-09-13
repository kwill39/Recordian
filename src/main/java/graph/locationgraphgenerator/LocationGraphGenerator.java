package graph.locationgraphgenerator;

import databasemanagement.DatabaseHelper;

import java.sql.*;

public class LocationGraphGenerator {

    final Integer getNumberOfTimesWorkedAtLocation(String locationName) {
        int timesWorkedAtLocation = 0;
        String sqlRead = "SELECT * FROM logEntries WHERE logEntryLocationName = ?";
        ResultSet resultSet = null;
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead)
        ) {
            preparedStatement.setString(1, locationName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                timesWorkedAtLocation++;
            }
            resultSet.close();
            return timesWorkedAtLocation;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
