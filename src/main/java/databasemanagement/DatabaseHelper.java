package databasemanagement;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <code>DatabaseHelper</code> is a helper class that contains methods used for
 * setting up and tearing down an Hour Tracker SQLite database.
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
public final class DatabaseHelper {
    /**
     * The url that leads to the SQLite database
     */
    public static final String DATABASE_PATH_NAME = "Hour_Tracker_Files/HourTracker.db";
    public static final String DATABASE_DIRECTORY_PATH_NAME = "Hour_Tracker_Files";
    public static final String DATABASE_CONNECTION_URL = "jdbc:sqlite:" + DATABASE_PATH_NAME;

    private DatabaseHelper(){}

    /**
     * Creates an Hour Tracker SQLite database - including the
     * tables which are accessed and used throughout Kyle's Hour Tracker
     */
    public static void createDatabase() {
        // Creates a new database file with appropriate tables
        new File(DATABASE_DIRECTORY_PATH_NAME).mkdir();
        try (Connection dbConnection = DriverManager.getConnection(DATABASE_CONNECTION_URL);) {
            Statement statement = dbConnection.createStatement();

            // Creates Companies table
            StringBuilder createCompaniesTable = new StringBuilder("CREATE TABLE IF NOT EXISTS companies (")
                    .append("companyID integer primary key,")
                    .append("companyName text NOT NULL UNIQUE")
                    .append(");");
            statement.execute(createCompaniesTable.toString());

            // Creates Locations table
            StringBuilder createLocationsTable = new StringBuilder("CREATE TABLE IF NOT EXISTS locations(")
                    .append("locationID integer primary key,")
                    .append("locationName text NOT NULL UNIQUE,")
                    .append("locationAddress text NOT NULL,")
                    .append("locationCity text NOT NULL,")
                    .append("locationState text NOT NULL,")
                    .append("locationZipCode text NOT NULL")
                    .append(");");
            statement.execute(createLocationsTable.toString());

            // Creates Supervisors table
            StringBuilder createSupervisorsTable = new StringBuilder("CREATE TABLE IF NOT EXISTS supervisors(")
                    .append("supervisorID integer primary key,")
                    .append("supervisorFirstName text NOT NULL,")
                    .append("supervisorLastName text NOT NULL,")
                    .append("supervisorDisplayName text NOT NULL UNIQUE")
                    .append(");");
            statement.execute(createSupervisorsTable.toString());

            // Creates Log Entries table
            StringBuilder createLogEntriesTable = new StringBuilder("CREATE TABLE IF NOT EXISTS logEntries(")
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
            statement.execute(createLogEntriesTable.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the SQLite database used in Kyle's Hour Tracker.
     * This method is commonly used by test classes in order to
     * recreate the database for test methods to run on a fresh database.
     */
    public static void deleteDatabase(){
        File databaseFile = new File(DATABASE_PATH_NAME);
        databaseFile.delete();
    }
}