package graph.companygraphgenerator;

import databasemanagement.DatabaseHelper;

import java.sql.*;

abstract class CompanyGraphGenerator {

    final Integer getNumberOfTimesWorkedForCompany(String companyName) {
        int timesWorkedForCompany = 0;
        String sqlRead = "SELECT * FROM logEntries WHERE logEntryCompanyName = ?";
        ResultSet resultSet = null;
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead)
        ) {
            preparedStatement.setString(1, companyName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                timesWorkedForCompany++;
            }
            resultSet.close();
            return timesWorkedForCompany;
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
