package com.kylewill;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseHelper is a helper class that contains methods used for
 * setting up and tearing down an Hour Tracker SQLite database.
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
public final class DatabaseHelper {
    public static final String DATABASE_CONNECTION_URL = "jdbc:sqlite:Hour_Tracker_Files/HourTracker.db";

    private DatabaseHelper(){}

    /**
     * Creates an Hour Tracker SQLite database - including the
     * tables which are accessed and used throughout Kyle's Hour Tracker
     */
    public static void createDatabase() {
        // Creates a new database file with appropriate tables
        new File("Hour_Tracker_Files").mkdir();
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DATABASE_CONNECTION_URL);
            String createCompaniesTable = "CREATE TABLE IF NOT EXISTS companies ("
                    + "companyID integer primary key,"
                    + "companyName text NOT NULL UNIQUE"
                    + ");";
            Statement statement = dbConnection.createStatement();
            statement.execute(createCompaniesTable);
            String createLocationsTable = "CREATE TABLE IF NOT EXISTS locations("
                    + "locationID integer primary key,"
                    + "locationName text NOT NULL UNIQUE,"
                    + "locationAddress text,"
                    + "locationCity text,"
                    + "locationState text,"
                    + "locationZipCode text"
                    + ");";
            statement.execute(createLocationsTable);
            String createSupervisorsTable = "CREATE TABLE IF NOT EXISTS supervisors("
                    + "supervisorID integer primary key,"
                    + "supervisorFirstName text,"
                    + "supervisorLastName text,"
                    + "supervisorDisplayName text NOT NULL UNIQUE"
                    + ");";
            statement.execute(createSupervisorsTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Deletes the SQLite database used in Kyle's Hour Tracker.
     * This method is commonly used by test classes in order to
     * recreate the database for test methods to run on a fresh database.
     */
    public static void deleteDatabase(){
        File databaseFile = new File("Hour_Tracker_Files/HourTracker.db");
        databaseFile.delete();
    }
}
