package com.kylewill;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseHelper {
    public static final String DATABASE_CONNECTION_URL = "jdbc:sqlite:Hour_Tracker_Files/HourTracker.db";

    private DatabaseHelper(){}

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

    // Deletes the database
    public static void deleteDatabase(){
        File databaseFile = new File("Hour_Tracker_Files/HourTracker.db");
        databaseFile.delete();
    }
}
