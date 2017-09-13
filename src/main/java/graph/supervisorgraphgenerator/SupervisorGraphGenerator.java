package graph.supervisorgraphgenerator;

import databasemanagement.DatabaseHelper;

import java.sql.*;

public class SupervisorGraphGenerator {
    final Integer getNumberOfTimesWorkedUnderSupervisor(String supervisorDisplayName) {
        int timesWorkedUnderSupervisor = 0;
        String sqlRead = "SELECT * FROM logEntries WHERE logEntrySupervisorDisplayName = ?";
        ResultSet resultSet = null;
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead)
        ) {
            preparedStatement.setString(1, supervisorDisplayName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                timesWorkedUnderSupervisor++;
            }
            resultSet.close();
            return timesWorkedUnderSupervisor;
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
