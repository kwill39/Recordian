package com.kylewill.databasemanagement.objectrelationalmap;

import com.kylewill.databasemanagement.DatabaseHelper;
import com.kylewill.model.Company;

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
public final class CompanyMapper implements DatabaseItemMapper<Company> {

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
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param   companyID the ID of the <code>Company</code>
     * @return  the <code>Company</code> whose ID matches that of <code>companyID</code>
     */
    @Override
    public Company read(int companyID) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlRead = "SELECT * FROM companies WHERE companyID = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead);
            preparedStatement.setInt(1, companyID);
            ResultSet resultSet = preparedStatement.executeQuery();
            Company fetchedCompany = new Company(resultSet.getString("companyName"));
            fetchedCompany.setCompanyID(resultSet.getInt("companyID"));
            return fetchedCompany;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param   companyName the unique name of the <code>Company</code>
     * @return  the <code>Company</code> whose <code>companyName</code> matches that of <code>companyName</code>
     */
    @Override
    public Company read(String companyName) {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
            String sqlRead = "SELECT * FROM companies WHERE companyName = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlRead);
            preparedStatement.setString(1, companyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            Company fetchedCompany = new Company(resultSet.getString("companyName"));
            fetchedCompany.setCompanyID(resultSet.getInt("companyID"));
            return fetchedCompany;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
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
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
