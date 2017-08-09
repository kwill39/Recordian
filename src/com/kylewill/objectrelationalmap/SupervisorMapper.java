package com.kylewill.objectrelationalmap;

import com.kylewill.DatabaseHelper;
import com.kylewill.model.Supervisor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class SupervisorMapper {

    private SupervisorMapper() {}

    public static void create(Supervisor supervisor) {
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

    public static List<Supervisor> readAll() {
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

    public static void update(Supervisor supervisor) {
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

    public static void delete(Supervisor supervisor) {
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