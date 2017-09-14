package chart;

import databasemanagement.DatabaseHelper;

import java.sql.*;

public abstract class ChartFactory {

    /**
     * Calculates the number of log entries that contain the value of the argument given
     *
     * @param databaseItemUniqueIdentifierName the unique name by which an individual {@link model.DatabaseItem} can be identified
     * @return an Integer representing the number of log entries that contain the value of the argument given
     */
    protected final Integer getNumberOfLogEntriesContainingSpecifiedDatabaseItem(String databaseItemUniqueIdentifierName) {
        int numberOfLogEntries = 0;
        ResultSet resultSet = null;
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery())
        ) {
            preparedStatement.setString(1, databaseItemUniqueIdentifierName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                numberOfLogEntries++;
            }
            return numberOfLogEntries;
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

    /**
     * Provides the SQL string to be used by the getNumberOfLogEntriesContainingSpecifiedDatabaseItem method
     *
     * @return an SQL query string
     */
    protected abstract String sqlQuery();

    /**
     * Provides the title of the chart
     *
     * @return a String representing the title of the chart
     */
    protected abstract String chartTitle();
}
