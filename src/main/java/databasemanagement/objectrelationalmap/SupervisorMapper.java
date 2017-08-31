package databasemanagement.objectrelationalmap;

import databasemanagement.DatabaseChangeObservable;
import databasemanagement.DatabaseHelper;
import model.Supervisor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class SupervisorMapper implements DatabaseItemMapper<Supervisor> {

    /**
     * {@inheritDoc}
     *
     * @param supervisor a <code>Supervisor<code/> object
     */
    @Override
    public void create(Supervisor supervisor) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlInsert = "INSERT INTO supervisors(supervisorFirstName,supervisorLastName,"
                    + "supervisorDisplayName) VALUES(?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlInsert);
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
     * @param   supervisorID the ID of the <code>DatabaseItem</code>
     * @return  the <code>Supervisor</code> whose ID matches that of <code>supervisorID</code>
     */
    @Override
    public Supervisor read(int supervisorID) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlRead = "SELECT * FROM supervisors WHERE supervisorID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead);
            preparedStatement.setInt(1, supervisorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            Supervisor fetchedSupervisor = new Supervisor(resultSet.getString("supervisorDisplayName"));
            fetchedSupervisor.setSupervisorID(resultSet.getInt("supervisorID"));
            fetchedSupervisor.setSupervisorFirstName(resultSet.getString("supervisorFirstName"));
            fetchedSupervisor.setSupervisorLastName(resultSet.getString("supervisorLastName"));
            return fetchedSupervisor;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the {@link Supervisor} whose supervisor name matches the supervisor name given as an argument
     *
     * @param   supervisorDisplayName the unique name of the <code>DatabaseItem</code>
     * @return  the <code>Supervisor</code> whose <code>supervisorDisplayName</code> matches that of <code>supervisorDisplayName</code>
     */
    public Supervisor read(String supervisorDisplayName) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlRead = "SELECT * FROM supervisors WHERE supervisorDisplayName = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead);
            preparedStatement.setString(1, supervisorDisplayName);
            ResultSet resultSet = preparedStatement.executeQuery();
            Supervisor fetchedSupervisor = new Supervisor(resultSet.getString("supervisorDisplayName"));
            fetchedSupervisor.setSupervisorID(resultSet.getInt("supervisorID"));
            fetchedSupervisor.setSupervisorFirstName(resultSet.getString("supervisorFirstName"));
            fetchedSupervisor.setSupervisorLastName(resultSet.getString("supervisorLastName"));
            return fetchedSupervisor;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return a <code>List</code> of <code>Supervisor<code/> objects
     */
    @Override
    public List<Supervisor> readAll() {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlQuery = "SELECT * FROM supervisors";
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
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
     * @param supervisor a <code>Supervisor<code/> object that has a valid primary key
     */
    @Override
    public void update(Supervisor supervisor) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlUpdate = "UPDATE supervisors SET supervisorFirstName = ?,"
                    + "supervisorLastName = ?,"
                    + "supervisorDisplayName = ?"
                    + "WHERE supervisorID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlUpdate);
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
     * @param supervisor <code>Supervisor<code/> object that has a valid primary key
     */
    @Override
    public void delete(Supervisor supervisor) {
        try (Connection dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL)) {
            String sqlDelete = "DELETE FROM supervisors WHERE supervisorID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, supervisor.getSupervisorID());
            preparedStatement.executeUpdate();
            DatabaseChangeObservable.notifyOfDelete(supervisor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}