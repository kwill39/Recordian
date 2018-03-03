package databasemanagement;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A helper class that contains methods used for
 * setting up and tearing down the SQLite database.
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
public final class DatabaseHelper {
    /**
     * The url that leads to the SQLite database
     */
    public static final String DATABASE_PATH_NAME = "Recordian_Files/Recordian.db";
    public static final String DATABASE_DIRECTORY_PATH_NAME = "Recordian_Files";
    public static final String DATABASE_CONNECTION_URL = "jdbc:sqlite:" + DATABASE_PATH_NAME;
    public static final String COMPANIES_TABLE_NAME = "companies";
    public static final String LOCATIONS_TABLE_NAME = "locations";
    public static final String SUPERVISORS_TABLE_NAME = "supervisors";
    public static final String LOG_ENTRIES_TABLE_NAME = "logEntries";
    private Statement sqlStatement;

    /**
     * Creates an SQLite database - including the
     * tables which are accessed and used throughout the application
     */
    public void createDatabase() {
        // Creates a new database file with appropriate tables
        new File(DATABASE_DIRECTORY_PATH_NAME).mkdir();
        try (Connection dbConnection = DriverManager.getConnection(DATABASE_CONNECTION_URL);) {
            sqlStatement = dbConnection.createStatement();
            createCompaniesTable();
            createLocationsTable();
            createSupervisorsTable();
            createLogEntriesTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createCompaniesTable() throws SQLException {
        StringBuilder createCompaniesTable = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(COMPANIES_TABLE_NAME)
                .append("(")
                .append("companyID integer primary key,")
                .append("companyName text NOT NULL UNIQUE")
                .append(");");
        sqlStatement.execute(createCompaniesTable.toString());
    }

    private void createLocationsTable() throws SQLException {
        StringBuilder createLocationsTable = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(LOCATIONS_TABLE_NAME)
                .append("(")
                .append("locationID integer primary key,")
                .append("locationName text NOT NULL UNIQUE,")
                .append("locationAddress text NOT NULL,")
                .append("locationCity text NOT NULL,")
                .append("locationState text NOT NULL,")
                .append("locationZipCode text NOT NULL")
                .append(");");
        sqlStatement.execute(createLocationsTable.toString());
    }

    private void createSupervisorsTable() throws SQLException {
        StringBuilder createSupervisorsTable = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(SUPERVISORS_TABLE_NAME)
                .append("(")
                .append("supervisorID integer primary key,")
                .append("supervisorFirstName text NOT NULL,")
                .append("supervisorLastName text NOT NULL,")
                .append("supervisorDisplayName text NOT NULL UNIQUE")
                .append(");");
        sqlStatement.execute(createSupervisorsTable.toString());
    }

    private void createLogEntriesTable() throws SQLException {
        // Creates Log Entries table
        StringBuilder createLogEntriesTable = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(LOG_ENTRIES_TABLE_NAME)
                .append("(")
                .append("logEntryID integer primary key,")
                .append("logEntryDate text NOT NULL,")
                .append("logEntryHours text NOT NULL,")
                .append("logEntryComments text,")
                .append("logEntryLocationName text,")
                .append("logEntryLocationAddress text,")
                .append("logEntryLocationCity text,")
                .append("logEntryLocationState text,")
                .append("logEntryLocationZipCode text,")
                .append("logEntryCompanyName text,")
                .append("logEntrySupervisorFirstName text,")
                .append("logEntrySupervisorLastName text,")
                .append("logEntrySupervisorDisplayName text")
                .append(");");
        sqlStatement.execute(createLogEntriesTable.toString());
    }

    /**
     * Deletes the SQLite database
     * <p>
     * This method is commonly used by test classes in order to
     * recreate the database for test methods to run on a fresh database.
     */
    public void deleteDatabase(){
        File databaseFile = new File(DATABASE_PATH_NAME);
        databaseFile.delete();
    }
}