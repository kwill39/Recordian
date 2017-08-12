package com.kylewill.objectrelationalmap;

import com.kylewill.DatabaseHelper;
import com.kylewill.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class LocationMapper implements ObjectMapper<Location> {

    /**
     * {@inheritDoc}
     *
     * @param location a <code>Location<code/> object
     */
    public void create(Location location) {
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

    /**
     * {@inheritDoc}
     *
     * @param   locationID the ID of the <code>Location</code>
     * @return  the <code>Location</code> whose ID matches that of <code>locationID</code>
     */
    @Override
    public Location read(int locationID) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlRead = "SELECT * FROM locations WHERE locationID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead);
            preparedStatement.setInt(1, locationID);
            ResultSet resultSet = preparedStatement.executeQuery();
            Location fetchedLocation = new Location(resultSet.getString("locationName"));
            fetchedLocation.setLocationID(resultSet.getInt("locationID"));
            fetchedLocation.setLocationAddress(resultSet.getString("locationAddress"));
            fetchedLocation.setLocationCity(resultSet.getString("locationCity"));
            fetchedLocation.setLocationState(resultSet.getString("locationState"));
            fetchedLocation.setLocationZipCode(resultSet.getString("locationZipCode"));
            return fetchedLocation;
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

    /**
     * {@inheritDoc}
     *
     * @param   locationName the unique name of the <code>Location</code>
     * @return  the <code>Location</code> whose <code>locationName</code> matches that of <code>locationName</code>
     */
    @Override
    public Location read(String locationName) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlRead = "SELECT * FROM locations WHERE locationName = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead);
            preparedStatement.setString(1, locationName);
            ResultSet resultSet = preparedStatement.executeQuery();
            Location fetchedLocation = new Location(resultSet.getString("locationName"));
            fetchedLocation.setLocationID(resultSet.getInt("locationID"));
            fetchedLocation.setLocationAddress(resultSet.getString("locationAddress"));
            fetchedLocation.setLocationCity(resultSet.getString("locationCity"));
            fetchedLocation.setLocationState(resultSet.getString("locationState"));
            fetchedLocation.setLocationZipCode(resultSet.getString("locationZipCode"));
            return fetchedLocation;
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

    /**
     * {@inheritDoc}
     *
     * @return a <code>List</code> of <code>Location<code/> objects
     */
    public List<Location> readAll() {
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

    /**
     * {@inheritDoc}
     *
     * @param location a <code>Location<code/> object that has a valid primary key
     */
    public void update(Location location) {
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

    /**
     * {@inheritDoc}
     *
     * @param location a <code>Location<code/> object that has a valid primary key
     */
    public void delete(Location location) {
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
