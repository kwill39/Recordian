package com.kylewill.objectrelationalmap;

import com.kylewill.DatabaseHelper;
import com.kylewill.model.Company;
import com.kylewill.model.DatabaseItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object that interfaces between
 * the SQLite database and <code>Company</code> objects
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
public final class CompanyMapper implements ObjectMapper<Company> {

    /**
     * {@inheritDoc}
     *
     * @param company a <code>Company<code/> object
     */
    public void create(Company company) {
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

    /**
     * {@inheritDoc}
     *
     * @return a <code>List</code> of <code>Company<code/> objects
     */
    public List<Company> readAll() {
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

    /**
     * {@inheritDoc}
     *
     * @param company a <code>Company<code/> object that has a valid primary key
     */
    public void update(Company company) {
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

    /**
     * {@inheritDoc}
     *
     * @param company a <code>Company<code/> object that has a valid primary key
     */
    public void delete(Company company) {
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
