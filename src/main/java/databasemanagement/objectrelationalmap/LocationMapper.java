package databasemanagement.objectrelationalmap;

import databasemanagement.DatabaseChangeObservable;
import databasemanagement.DatabaseHelper;
import model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class LocationMapper implements DatabaseItemMapper<Location> {

    /**
     * {@inheritDoc}
     *
     * @param location a <code>Location<code/> object
     */
    @Override
    public void create(Location location) {
        String sqlInsert = "INSERT INTO locations(locationName, locationAddress, locationCity,"
                + "locationState, locationZipCode) VALUES(?,?,?,?,?);";
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlInsert)
        ) {
            preparedStatement.setString(1, location.getLocationName());
            preparedStatement.setString(2, location.getLocationAddress());
            preparedStatement.setString(3, location.getLocationCity());
            preparedStatement.setString(4, location.getLocationState());
            preparedStatement.setString(5, location.getLocationZipCode());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfCreation(read(location.getLocationName()));
        } catch (SQLException e) {
            e.printStackTrace();
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
        String sqlRead = "SELECT * FROM locations WHERE locationID = ?";
        ResultSet resultSet = null;
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead)
        ) {
            preparedStatement.setInt(1, locationID);
            resultSet = preparedStatement.executeQuery();
            Location fetchedLocation = new Location(resultSet.getString("locationName"));
            fetchedLocation.setLocationID(resultSet.getInt("locationID"));
            fetchedLocation.setLocationAddress(resultSet.getString("locationAddress"));
            fetchedLocation.setLocationCity(resultSet.getString("locationCity"));
            fetchedLocation.setLocationState(resultSet.getString("locationState"));
            fetchedLocation.setLocationZipCode(resultSet.getString("locationZipCode"));
            return fetchedLocation;
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
     * Retrieves the {@link Location} whose location name matches the location name given as an argument
     *
     * @param   locationName the unique name of the <code>Location</code>
     * @return  the <code>Location</code> whose <code>locationName</code> matches that of <code>locationName</code>
     */
    public Location read(String locationName) {
        String sqlRead = "SELECT * FROM locations WHERE locationName = ?";
        ResultSet resultSet = null;
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead)
        ) {
            preparedStatement.setString(1, locationName);
            resultSet = preparedStatement.executeQuery();
            Location fetchedLocation = new Location(resultSet.getString("locationName"));
            fetchedLocation.setLocationID(resultSet.getInt("locationID"));
            fetchedLocation.setLocationAddress(resultSet.getString("locationAddress"));
            fetchedLocation.setLocationCity(resultSet.getString("locationCity"));
            fetchedLocation.setLocationState(resultSet.getString("locationState"));
            fetchedLocation.setLocationZipCode(resultSet.getString("locationZipCode"));
            return fetchedLocation;
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
     * {@inheritDoc}
     *
     * @return a <code>List</code> of <code>Location<code/> objects
     */
    @Override
    public List<Location> readAll() {
        String sqlQuery = "SELECT * FROM locations";
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                Statement statement = dbConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param location a <code>Location<code/> object that has a valid primary key
     */
    @Override
    public void update(Location location) {
        StringBuilder sqlUpdate = new StringBuilder("UPDATE locations SET locationName = ?,")
                .append("locationAddress = ?,")
                .append("locationCity = ?,")
                .append("locationState = ?,")
                .append("locationZipCode = ?")
                .append("WHERE locationID = ?");
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlUpdate.toString());
        ) {
            preparedStatement.setString(1, location.getLocationName());
            preparedStatement.setString(2, location.getLocationAddress());
            preparedStatement.setString(3, location.getLocationCity());
            preparedStatement.setString(4, location.getLocationState());
            preparedStatement.setString(5, location.getLocationZipCode());
            preparedStatement.setInt(6, location.getLocationID());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfUpdate(location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param location a <code>Location<code/> object that has a valid primary key
     */
    @Override
    public void delete(Location location) {
        String sqlDelete = "DELETE FROM locations WHERE locationID = ?";
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlDelete)
        ) {
            preparedStatement.setInt(1, location.getLocationID());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfDelete(location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}