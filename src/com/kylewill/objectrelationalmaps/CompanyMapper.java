package com.kylewill.objectrelationalmaps;

import com.kylewill.DatabaseHelper;
import com.kylewill.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class CompanyMapper {

    private CompanyMapper() {}

    public static void create(Company company) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlInsert = "INSERT INTO companies(companyName) VALUES(?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, company.getCompanyName());
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

    public static List<Company> readAll() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlQuery = "SELECT * FROM companies";
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            List<Company> companyList = new ArrayList<>();
            Company someCompany;
            while(resultSet.next()){
                someCompany = new Company(resultSet.getString("companyName"));
                someCompany.setCompanyID(resultSet.getInt("companyID"));
                companyList.add(someCompany);
            }
            return companyList;
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

    public static void update(Company company) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlUpdate = "UPDATE companies SET companyName = ? WHERE companyID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setInt(2, company.getCompanyID());
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

    public static void delete(Company company) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlDelete = "DELETE FROM companies WHERE companyID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, company.getCompanyID());
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
