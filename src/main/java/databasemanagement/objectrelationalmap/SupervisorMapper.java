package databasemanagement.objectrelationalmap;

import databasemanagement.DatabaseChangeObservable;
import databasemanagement.DatabaseHelper;
import model.Supervisor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object that interfaces between
 * the SQLite database and {@link Supervisor} objects
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
public final class SupervisorMapper implements DatabaseItemMapper<Supervisor> {

    /**
     * {@inheritDoc}
     *
     * @param supervisor a {@link Supervisor} object
     */
    @Override
    public void create(Supervisor supervisor) {
        String sqlInsert = "INSERT INTO supervisors(supervisorFirstName,supervisorLastName,"
                + "supervisorDisplayName) VALUES(?,?,?)";
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlInsert)
        ) {
            preparedStatement.setString(1, supervisor.getSupervisorFirstName());
            preparedStatement.setString(2, supervisor.getSupervisorLastName());
            preparedStatement.setString(3, supervisor.getSupervisorDisplayName());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfCreation(read(supervisor.getSupervisorDisplayName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param   supervisorID the ID of the {@link Supervisor}
     * @return  the {@link Supervisor} whose ID matches that of <code>supervisorID</code>
     */
    @Override
    public Supervisor read(int supervisorID) {
        String sqlRead = "SELECT * FROM supervisors WHERE supervisorID = ?";
        ResultSet resultSet = null;
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead)
        ) {
            preparedStatement.setInt(1, supervisorID);
            resultSet = preparedStatement.executeQuery();
            Supervisor fetchedSupervisor = new Supervisor(resultSet.getString("supervisorDisplayName"));
            fetchedSupervisor.setSupervisorID(resultSet.getInt("supervisorID"));
            fetchedSupervisor.setSupervisorFirstName(resultSet.getString("supervisorFirstName"));
            fetchedSupervisor.setSupervisorLastName(resultSet.getString("supervisorLastName"));
            return fetchedSupervisor;
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
     * Retrieves the {@link Supervisor} whose supervisor name matches the supervisor name given as an argument
     *
     * @param   supervisorDisplayName the unique name of the {@link Supervisor}
     * @return  the {@link Supervisor} whose <code>supervisorDisplayName</code> matches that of <code>supervisorDisplayName</code>
     */
    public Supervisor read(String supervisorDisplayName) {
        String sqlRead = "SELECT * FROM supervisors WHERE supervisorDisplayName = ?";
        ResultSet resultSet = null;
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead)
        ) {
            preparedStatement.setString(1, supervisorDisplayName);
            resultSet = preparedStatement.executeQuery();
            Supervisor fetchedSupervisor = new Supervisor(resultSet.getString("supervisorDisplayName"));
            fetchedSupervisor.setSupervisorID(resultSet.getInt("supervisorID"));
            fetchedSupervisor.setSupervisorFirstName(resultSet.getString("supervisorFirstName"));
            fetchedSupervisor.setSupervisorLastName(resultSet.getString("supervisorLastName"));
            return fetchedSupervisor;
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
     * @return a {@link List} of {@link Supervisor} objects
     */
    @Override
    public List<Supervisor> readAll() {
        String sqlQuery = "SELECT * FROM supervisors";
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                Statement statement = dbConnection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
            List<Supervisor> supervisorList = new ArrayList<>();
            Supervisor someSupervisor;
            while(resultSet.next()){
                someSupervisor = new Supervisor(resultSet.getString("supervisorDisplayName"));
                someSupervisor.setSupervisorID(resultSet.getInt("supervisorID"));
                someSupervisor.setSupervisorFirstName(resultSet.getString("supervisorFirstName"));
                someSupervisor.setSupervisorLastName(resultSet.getString("supervisorLastName"));
                supervisorList.add(someSupervisor);
            }
            return supervisorList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param supervisor a {@link Supervisor} object that has a valid primary key
     */
    @Override
    public void update(Supervisor supervisor) {
        StringBuilder sqlUpdate = new StringBuilder("UPDATE supervisors SET supervisorFirstName = ?,")
                .append("supervisorLastName = ?,")
                .append("supervisorDisplayName = ?")
                .append("WHERE supervisorID = ?");
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlUpdate.toString())
        ) {
            preparedStatement.setString(1, supervisor.getSupervisorFirstName());
            preparedStatement.setString(2, supervisor.getSupervisorLastName());
            preparedStatement.setString(3, supervisor.getSupervisorDisplayName());
            preparedStatement.setInt(4, supervisor.getSupervisorID());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfUpdate(supervisor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param supervisor {@link Supervisor} object that has a valid primary key
     */
    @Override
    public void delete(Supervisor supervisor) {
        String sqlDelete = "DELETE FROM supervisors WHERE supervisorID = ?";
        try (
                Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
                PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlDelete)
        ) {
            preparedStatement.setInt(1, supervisor.getSupervisorID());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfDelete(supervisor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}