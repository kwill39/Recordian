package com.kylewill.objectrelationalmaps;

import com.kylewill.DatabaseHelper;
import com.kylewill.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class LocationMapper {

    private LocationMapper() {}

    public static void create(Location location) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlInsert = "INSERT INTO locations(locationName, locationAddress, locationCity,"
                    + "locationState, locationZipCode) VALUES(?,?,?,?,?);";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, location.getLocationName());
            preparedStatement.setString(2, location.getLocationAddress());
            preparedStatement.setString(3, location.getLocationCity());
            preparedStatement.setString(4, location.getLocationState());
            preparedStatement.setString(5, location.getLocationZipCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO: Handle exceptions
            System.err.println(e.getMessage());
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                // TODO: Handle exceptions
                System.err.println(e.getMessage());
            }
        }
    }

    public static List<Location> readAll() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlQuery = "SELECT * FROM locations";
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            List<Location> locationList = new ArrayList<>();
            Location someLocation;
            while(resultSet.next()){
                someLocation = new Location(resultSet.getString("locationName"));
                someLocation.setLocationID(resultSet.getInt("locationID"));
                someLocation.setLocationAddress(resultSet.getString("locationAddress"));
                someLocation.setLocationCity(resultSet.getString("locationCity"));
                someLocation.setLocationState(resultSet.getString("locationState"));
                someLocation.setLocationZipCode(resultSet.getString("locationZipCode"));
                locationList.add(someLocation);
            }
            return locationList;
        } catch (SQLException e) {
            // TODO: Handle exceptions
            System.err.println(e.getMessage());
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                // TODO: Handle exceptions
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public static void update(Location location) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlUpdate = "UPDATE locations SET locationName = ?,"
                    + "locationAddress = ?,"
                    + "locationCity = ?,"
                    + "locationState = ?,"
                    + "locationZipCode = ?"
                    + "WHERE locationID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, location.getLocationName());
            preparedStatement.setString(2, location.getLocationAddress());
            preparedStatement.setString(3, location.getLocationCity());
            preparedStatement.setString(4, location.getLocationState());
            preparedStatement.setString(5, location.getLocationZipCode());
            preparedStatement.setInt(6, location.getLocationID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO: Handle exceptions
            System.err.println(e.getMessage());
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                // TODO: Handle exceptions
                System.err.println(e.getMessage());
            }
        }
    }

    public static void delete(Location location) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlDelete = "DELETE FROM locations WHERE locationID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, location.getLocationID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO: Handle exceptions
            System.err.println(e.getMessage());
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                // TODO: Handle exceptions
                System.err.println(e.getMessage());
            }
        }
    }
}
