package databasemanagement.objectrelationalmap;

import databasemanagement.DatabaseChangeObservable;
import databasemanagement.DatabaseHelper;
import model.LogEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object that interfaces between
 * the SQLite database and {@link LogEntry} objects
 *
 * @author  Kyle Williams
 * @since   Version 3
 */
public class LogEntryMapper implements DatabaseItemMapper<LogEntry>{

    /**
     * {@inheritDoc}
     *
     * @param logEntry a {@link LogEntry} object
     */
    @Override
    public void create(LogEntry logEntry) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            StringBuilder sqlInsert = new StringBuilder("INSERT INTO logEntries(")
                    .append("logEntryDate,")
                    .append("logEntryHours,")
                    .append("logEntryComments,")
                    .append("logEntryLocationName,")
                    .append("logEntryLocationAddress,")
                    .append("logEntryLocationCity,")
                    .append("logEntryLocationState,")
                    .append("logEntryLocationZipCode,")
                    .append("logEntryCompanyName,")
                    .append("logEntrySupervisorFirstName,")
                    .append("logEntrySupervisorLastName,")
                    .append("logEntrySupervisorDisplayName)")
                    .append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?);");
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlInsert.toString(), Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, logEntry.getLogEntryDate());
            preparedStatement.setString(2, logEntry.getLogEntryHours());
            preparedStatement.setString(3, logEntry.getLogEntryComments());
            preparedStatement.setString(4, logEntry.getLogEntryLocationName());
            preparedStatement.setString(5, logEntry.getLogEntryLocationAddress());
            preparedStatement.setString(6, logEntry.getLogEntryLocationCity());
            preparedStatement.setString(7, logEntry.getLogEntryLocationState());
            preparedStatement.setString(8, logEntry.getLogEntryLocationZipCode());
            preparedStatement.setString(9, logEntry.getLogEntryCompanyName());
            preparedStatement.setString(10, logEntry.getLogEntrySupervisorFirstName());
            preparedStatement.setString(11, logEntry.getLogEntrySupervisorLastName());
            preparedStatement.setString(12, logEntry.getLogEntrySupervisorDisplayName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                logEntry.setLogEntryID(generatedKeys.getInt(1));
            }
            DatabaseChangeObservable.notifyOfCreation(logEntry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param logEntryID the ID of the {@link LogEntry}
     * @return the {@link LogEntry} whose ID matches that of <code>logEntryID</code>
     */
    @Override
    public LogEntry read(int logEntryID) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlRead = "SELECT * FROM logEntries WHERE logEntryID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead);
            preparedStatement.setInt(1, logEntryID);
            ResultSet resultSet = preparedStatement.executeQuery();
            LogEntry fetchedLogEntry = new LogEntry(resultSet.getString("logEntryDate"),
                    resultSet.getString("logEntryHours"));
            fetchedLogEntry.setLogEntryID(resultSet.getInt("logEntryID"));
            fetchedLogEntry.setLogEntryComments(resultSet.getString("logEntryComments"));
            fetchedLogEntry.setLogEntryLocationName(resultSet.getString("logEntryLocationName"));
            fetchedLogEntry.setLogEntryLocationAddress(resultSet.getString("logEntryLocationAddress"));
            fetchedLogEntry.setLogEntryLocationCity(resultSet.getString("logEntryLocationCity"));
            fetchedLogEntry.setLogEntryLocationState(resultSet.getString("logEntryLocationState"));
            fetchedLogEntry.setLogEntryLocationZipCode(resultSet.getString("logEntryLocationZipCode"));
            fetchedLogEntry.setLogEntryCompanyName(resultSet.getString("logEntryCompanyName"));
            fetchedLogEntry.setLogEntrySupervisorFirstName(resultSet.getString("logEntrySupervisorFirstName"));
            fetchedLogEntry.setLogEntrySupervisorLastName(resultSet.getString("logEntrySupervisorLastName"));
            fetchedLogEntry.setLogEntrySupervisorDisplayName(resultSet.getString("logEntrySupervisorDisplayName"));
            return fetchedLogEntry;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return a {@link List} of {@link LogEntry} objects
     */
    @Override
    public List<LogEntry> readAll() {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlQuery = "SELECT * FROM logEntries";
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            List<LogEntry> logEntryList = new ArrayList<>();
            LogEntry someLogEntry;
            while(resultSet.next()){
                someLogEntry = new LogEntry(resultSet.getString("logEntryDate"),
                        resultSet.getString("logEntryHours"));
                someLogEntry.setLogEntryID(resultSet.getInt("logEntryID"));
                someLogEntry.setLogEntryComments(resultSet.getString("logEntryComments"));
                someLogEntry.setLogEntryLocationName(resultSet.getString("logEntryLocationName"));
                someLogEntry.setLogEntryLocationAddress(resultSet.getString("logEntryLocationAddress"));
                someLogEntry.setLogEntryLocationCity(resultSet.getString("logEntryLocationCity"));
                someLogEntry.setLogEntryLocationState(resultSet.getString("logEntryLocationState"));
                someLogEntry.setLogEntryLocationZipCode(resultSet.getString("logEntryLocationZipCode"));
                someLogEntry.setLogEntryCompanyName(resultSet.getString("logEntryCompanyName"));
                someLogEntry.setLogEntrySupervisorFirstName(resultSet.getString("logEntrySupervisorFirstName"));
                someLogEntry.setLogEntrySupervisorLastName(resultSet.getString("logEntrySupervisorLastName"));
                someLogEntry.setLogEntrySupervisorDisplayName(resultSet.getString("logEntrySupervisorDisplayName"));
                logEntryList.add(someLogEntry);
            }
            return logEntryList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param logEntry a {@link LogEntry} object that has a valid primary key
     */
    @Override
    public void update(LogEntry logEntry) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            StringBuilder sqlUpdate = new StringBuilder("UPDATE logEntries SET logEntryDate = ?,")
                    .append("logEntryHours = ?,")
                    .append("logEntryComments = ?,")
                    .append("logEntryLocationName = ?,")
                    .append("logEntryLocationAddress = ?,")
                    .append("logEntryLocationCity = ?,")
                    .append("logEntryLocationState = ?,")
                    .append("logEntryLocationZipCode = ?,")
                    .append("logEntryCompanyName = ?,")
                    .append("logEntrySupervisorFirstName = ?,")
                    .append("logEntrySupervisorLastName = ?,")
                    .append("logEntrySupervisorDisplayName = ?")
                    .append("WHERE logEntryID = ?");
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlUpdate.toString());
            preparedStatement.setString(1, logEntry.getLogEntryDate());
            preparedStatement.setString(2, logEntry.getLogEntryHours());
            preparedStatement.setString(3, logEntry.getLogEntryComments());
            preparedStatement.setString(4, logEntry.getLogEntryLocationName());
            preparedStatement.setString(5, logEntry.getLogEntryLocationAddress());
            preparedStatement.setString(6, logEntry.getLogEntryLocationCity());
            preparedStatement.setString(7, logEntry.getLogEntryLocationState());
            preparedStatement.setString(8, logEntry.getLogEntryLocationZipCode());
            preparedStatement.setString(9, logEntry.getLogEntryCompanyName());
            preparedStatement.setString(10, logEntry.getLogEntrySupervisorFirstName());
            preparedStatement.setString(11, logEntry.getLogEntrySupervisorLastName());
            preparedStatement.setString(12, logEntry.getLogEntrySupervisorDisplayName());
            preparedStatement.setInt(13, logEntry.getLogEntryID());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfUpdate(logEntry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param logEntry a {@link LogEntry} object that has a valid primary key
     */
    @Override
    public void delete(LogEntry logEntry) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlDelete = "DELETE FROM logEntries WHERE logEntryID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, logEntry.getLogEntryID());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfDelete(logEntry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
