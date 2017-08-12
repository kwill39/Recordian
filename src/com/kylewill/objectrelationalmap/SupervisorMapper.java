package com.kylewill.objectrelationalmap;

import com.kylewill.DatabaseHelper;
import com.kylewill.model.Supervisor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class SupervisorMapper implements DatabaseItemMapper<Supervisor> {

    /**
     * {@inheritDoc}
     *
     * @param supervisor a <code>Supervisor<code/> object
     */
    public void create(Supervisor supervisor) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlInsert = "INSERT INTO supervisors(supervisorFirstName,supervisorLastName,"
                    + "supervisorDisplayName) VALUES(?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, supervisor.getSupervisorFirstName());
            preparedStatement.setString(2, supervisor.getSupervisorLastName());
            preparedStatement.setString(3, supervisor.getSupervisorDisplayName());
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
     * @param   supervisorID the ID of the <code>DatabaseItem</code>
     * @return  the <code>Supervisor</code> whose ID matches that of <code>supervisorID</code>
     */
    @Override
    public Supervisor read(int supervisorID) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
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
     * @param   supervisorDisplayName the unique name of the <code>DatabaseItem</code>
     * @return  the <code>Supervisor</code> whose <code>supervisorDisplayName</code> matches that of <code>supervisorDisplayName</code>
     */
    @Override
    public Supervisor read(String supervisorDisplayName) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
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
     * @return a <code>List</code> of <code>Supervisor<code/> objects
     */
    public List<Supervisor> readAll() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
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
     * @param supervisor a <code>Supervisor<code/> object that has a valid primary key
     */
    public void update(Supervisor supervisor) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
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
     * @param supervisor <code>Supervisor<code/> object that has a valid primary key
     */
    public void delete(Supervisor supervisor) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlDelete = "DELETE FROM supervisors WHERE supervisorID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, supervisor.getSupervisorID());
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
