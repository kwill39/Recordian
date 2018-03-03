package databasemanagement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.*;

import static org.junit.Assert.*;

public class DatabaseHelperTest {

    private DatabaseHelper databaseHelper = new DatabaseHelper();
    private File database = new File(DatabaseHelper.DATABASE_PATH_NAME);
    private Connection databaseConnection;
    private DatabaseMetaData databaseMetaData;

    @Before
    public void setUp() throws Exception {
        databaseHelper.deleteDatabase();
    }

    @After
    public void tearDown() throws Exception {
        databaseHelper.deleteDatabase();
    }

    @Test
    public void createDatabase() throws Exception {
        databaseHelper.createDatabase();
        assertTrue(database.exists());
        verifyDatabaseHasCorrectTablesAndFields();
    }

    private void verifyDatabaseHasCorrectTablesAndFields() throws SQLException {
        databaseConnection = DriverManager.getConnection(DatabaseHelper.DATABASE_CONNECTION_URL);
        // Get the database metadata to be able see what tables and fields exist
        databaseMetaData = databaseConnection.getMetaData();
        verifyCompaniesTableAndFieldsExist();
        verifyLocationsTableAndFieldsExist();
        verifySupervisorsTableAndFieldsExist();
        verifySupervisorsTableAndFieldsExist();
    }

    private void verifyCompaniesTableAndFieldsExist() throws SQLException {
        assertTrue(DatabaseHelper.COMPANIES_TABLE_NAME + " table does not exist",
                doesDatabaseTableExist(DatabaseHelper.COMPANIES_TABLE_NAME));
        assertTrue("companyID field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.COMPANIES_TABLE_NAME, "companyID"));
        assertTrue("companyName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.COMPANIES_TABLE_NAME, "companyName"));
    }

    private void verifyLocationsTableAndFieldsExist() throws SQLException {
        assertTrue(DatabaseHelper.LOCATIONS_TABLE_NAME + " table does not exist",
                doesDatabaseTableExist(DatabaseHelper.LOCATIONS_TABLE_NAME));
        assertTrue("locationID field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOCATIONS_TABLE_NAME, "locationID"));
        assertTrue("locationName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOCATIONS_TABLE_NAME, "locationName"));
        assertTrue("locationAddress field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOCATIONS_TABLE_NAME, "locationAddress"));
        assertTrue("locationCity field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOCATIONS_TABLE_NAME, "locationCity"));
        assertTrue("locationState field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOCATIONS_TABLE_NAME, "locationState"));
        assertTrue("locationZipCode field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOCATIONS_TABLE_NAME, "locationZipCode"));
    }

    private void verifySupervisorsTableAndFieldsExist() throws SQLException {
        assertTrue(DatabaseHelper.SUPERVISORS_TABLE_NAME + " table does not exist",
                doesDatabaseTableExist(DatabaseHelper.SUPERVISORS_TABLE_NAME));
        assertTrue("supervisorID field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.SUPERVISORS_TABLE_NAME, "supervisorID"));
        assertTrue("supervisorFirstName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.SUPERVISORS_TABLE_NAME, "supervisorFirstName"));
        assertTrue("supervisorLastName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.SUPERVISORS_TABLE_NAME, "supervisorLastName"));
        assertTrue("supervisorDisplayName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.SUPERVISORS_TABLE_NAME, "supervisorDisplayName"));
    }

    private void  verifyLogEntriesTableAndFieldsExist() throws SQLException {
        assertTrue(DatabaseHelper.LOG_ENTRIES_TABLE_NAME + " table does not exist",
                doesDatabaseTableExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME));
        assertTrue("logEntryID field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryID"));
        assertTrue("logEntryDate field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryDate"));
        assertTrue("logEntryHours field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryHours"));
        assertTrue("logEntryComments field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryComments"));
        assertTrue("logEntryLocationName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryLocationName"));
        assertTrue("logEntryLocationAddress field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryLocationAddress"));
        assertTrue("logEntryLocationCity field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryLocationCity"));
        assertTrue("logEntryLocationState field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryLocationState"));
        assertTrue("logEntryLocationZipCode field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryLocationZipCode"));
        assertTrue("logEntryCompanyName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntryCompanyName"));
        assertTrue("logEntrySupervisorFirstName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntrySupervisorFirstName"));
        assertTrue("logEntrySupervisorLastName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntrySupervisorLastName"));
        assertTrue("logEntrySupervisorDisplayName field does not exist",
                doesDatabaseFieldExist(DatabaseHelper.LOG_ENTRIES_TABLE_NAME, "logEntrySupervisorDisplayName"));
    }

    private boolean doesDatabaseTableExist(String tableName) throws SQLException {
        ResultSet tablesResultSet =
                databaseMetaData.getTables(null, null, tableName, null);
        return tablesResultSet.next();
    }

    private boolean doesDatabaseFieldExist(String tableName, String fieldName) throws SQLException {
        ResultSet fieldsResultSet =
                databaseMetaData.getColumns(null, null, tableName, fieldName);
        return fieldsResultSet.next();
    }

    @Test
    public void deleteDatabase() throws Exception {
        databaseHelper.createDatabase();
        assertTrue(database.exists());
        databaseHelper.deleteDatabase();
        assertFalse(database.exists());
    }

}