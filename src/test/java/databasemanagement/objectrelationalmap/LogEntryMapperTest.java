package databasemanagement.objectrelationalmap;

import databasemanagement.DatabaseHelper;
import model.LogEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class LogEntryMapperTest implements DatabaseItemMapperTest<LogEntry> {
    private LogEntryMapper logEntryMapper = new LogEntryMapper();
    private LogEntry logEntry1;
    private LogEntry logEntry2;
    private LogEntry logEntry3;
    private List<LogEntry> logEntryList;
    private LogEntry dbLogEntry1;
    private LogEntry dbLogEntry2;
    private LogEntry dbLogEntry3;

    @Before
    public void populateDatabase(){
        DatabaseHelper.deleteDatabase();
        DatabaseHelper.createDatabase();
        // Create the records
        logEntry1 = new LogEntry("2017-09-1", "1");
        logEntry1.setLogEntryComments("One");
        logEntry1.setLogEntryLocationName("Location1");
        logEntry1.setLogEntryLocationAddress("Address1");
        logEntry1.setLogEntryLocationCity("City1");
        logEntry1.setLogEntryLocationState("State1");
        logEntry1.setLogEntryLocationZipCode("ZipCode1");
        logEntry1.setLogEntryCompanyName("Company1");
        logEntry1.setLogEntrySupervisorFirstName("Supervisor");
        logEntry1.setLogEntrySupervisorLastName("1");
        logEntry1.setLogEntrySupervisorDisplayName("Supervisor 1");
        logEntry2 = new LogEntry("2017-09-2", "2");
        logEntry2.setLogEntryComments("Two");
        logEntry2.setLogEntryLocationName("Location2");
        logEntry2.setLogEntryLocationAddress("Address2");
        logEntry2.setLogEntryLocationCity("City2");
        logEntry2.setLogEntryLocationState("State2");
        logEntry2.setLogEntryLocationZipCode("ZipCode2");
        logEntry2.setLogEntryCompanyName("Company2");
        logEntry2.setLogEntrySupervisorFirstName("Supervisor");
        logEntry2.setLogEntrySupervisorLastName("2");
        logEntry2.setLogEntrySupervisorDisplayName("Supervisor 2");
        logEntry3 = new LogEntry("2017-09-3", "3");
        logEntry3.setLogEntryComments("Three");
        logEntry3.setLogEntryLocationName("Location3");
        logEntry3.setLogEntryLocationAddress("Address3");
        logEntry3.setLogEntryLocationCity("City3");
        logEntry3.setLogEntryLocationState("State3");
        logEntry3.setLogEntryLocationZipCode("ZipCode3");
        logEntry3.setLogEntryCompanyName("Company3");
        logEntry3.setLogEntrySupervisorFirstName("Supervisor");
        logEntry3.setLogEntrySupervisorLastName("3");
        logEntry3.setLogEntrySupervisorDisplayName("Supervisor 3");
        logEntryMapper.create(logEntry1);
        logEntryMapper.create(logEntry2);
        logEntryMapper.create(logEntry3);

        // Read the records
        logEntryList = logEntryMapper.readAll();
        dbLogEntry1 = logEntryList.get(0);
        dbLogEntry2 = logEntryList.get(1);
        dbLogEntry3 = logEntryList.get(2);
    }

    @After
    public void deleteDatabase(){
        DatabaseHelper.deleteDatabase();
    }

    // Testing to see that the records were read properly also
    // inherently tests to see that the records were created properly.
    // As a result, there is no need for a separate test method to test
    // the create method of the mapper class.

    @Test
    public void read() {
        LogEntry dbReadLogEntry1 = logEntryMapper.read(dbLogEntry1.getLogEntryID());
        LogEntry dbReadLogEntry2 = logEntryMapper.read(dbLogEntry2.getLogEntryID());
        LogEntry dbReadLogEntry3 = logEntryMapper.read(dbLogEntry3.getLogEntryID());
        assertTrue(objectsAreEqual(dbLogEntry1, dbReadLogEntry1));
        assertTrue(objectsAreEqual(dbLogEntry2, dbReadLogEntry2));
        assertTrue(objectsAreEqual(dbReadLogEntry3, dbReadLogEntry3));
    }

    @Test
    public void readAll() {
        // All records are created and read during the Before method.
        // This method only serves to verify that the records were properly read.
        assertTrue(dbLogEntry1.getLogEntryID() == 1 && objectsAreEqual(dbLogEntry1, logEntry1));
        assertTrue(dbLogEntry2.getLogEntryID() == 2 && objectsAreEqual(dbLogEntry2, logEntry2));
        assertTrue(dbLogEntry3.getLogEntryID() == 3 && objectsAreEqual(dbLogEntry3, logEntry3));
    }

    @Test
    public void update(){
        // Update the records
        dbLogEntry1.setLogEntryDate("2017-09-4");
        dbLogEntry1.setLogEntryHours("4");
        dbLogEntry1.setLogEntryComments("Four");
        dbLogEntry1.setLogEntryLocationName("Location4");
        dbLogEntry1.setLogEntryLocationAddress("Address4");
        dbLogEntry1.setLogEntryLocationCity("City4");
        dbLogEntry1.setLogEntryLocationState("State4");
        dbLogEntry1.setLogEntryLocationZipCode("ZipCode4");
        dbLogEntry1.setLogEntryCompanyName("Company4");
        dbLogEntry1.setLogEntrySupervisorFirstName("Superman");
        dbLogEntry1.setLogEntrySupervisorLastName("4");
        dbLogEntry1.setLogEntrySupervisorDisplayName("Superman 4");
        logEntryMapper.update(dbLogEntry1);

        dbLogEntry2.setLogEntryDate("2017-09-5");
        dbLogEntry2.setLogEntryHours("5");
        dbLogEntry2.setLogEntryComments("Five");
        dbLogEntry2.setLogEntryLocationName("Location5");
        dbLogEntry2.setLogEntryLocationAddress("Address5");
        dbLogEntry2.setLogEntryLocationCity("City5");
        dbLogEntry2.setLogEntryLocationState("State5");
        dbLogEntry2.setLogEntryLocationZipCode("ZipCode5");
        dbLogEntry2.setLogEntryCompanyName("Company5");
        dbLogEntry2.setLogEntrySupervisorFirstName("Superman");
        dbLogEntry2.setLogEntrySupervisorLastName("5");
        dbLogEntry2.setLogEntrySupervisorDisplayName("Superman 5");
        logEntryMapper.update(dbLogEntry2);

        dbLogEntry3.setLogEntryDate("2017-09-6");
        dbLogEntry3.setLogEntryHours("6");
        dbLogEntry3.setLogEntryComments("Six");
        dbLogEntry3.setLogEntryLocationName("Location6");
        dbLogEntry3.setLogEntryLocationAddress("Address6");
        dbLogEntry3.setLogEntryLocationCity("City6");
        dbLogEntry3.setLogEntryLocationState("State6");
        dbLogEntry3.setLogEntryLocationZipCode("ZipCode6");
        dbLogEntry3.setLogEntryCompanyName("Company6");
        dbLogEntry3.setLogEntrySupervisorFirstName("Superman");
        dbLogEntry3.setLogEntrySupervisorLastName("6");
        dbLogEntry3.setLogEntrySupervisorDisplayName("Superman 6");
        logEntryMapper.update(dbLogEntry3);

        // Verify the records were updated
        logEntryList = logEntryMapper.readAll();
        LogEntry updatedLogEntry1 = logEntryList.get(0);
        LogEntry updatedLogEntry2 = logEntryList.get(1);
        LogEntry updatedLogEntry3 = logEntryList.get(2);

        assertTrue(objectsAreEqual(dbLogEntry1, updatedLogEntry1));
        assertTrue(objectsAreEqual(dbLogEntry2, updatedLogEntry2));
        assertTrue(objectsAreEqual(dbLogEntry3, updatedLogEntry3));
    }

    @Test
    public void delete(){
        // Delete the records one by one and verify that they were deleted along the way
        logEntryMapper.delete(dbLogEntry1);
        logEntryList = logEntryMapper.readAll();
        for (LogEntry someLogEntry : logEntryList) {
            assertTrue(someLogEntry.getLogEntryID() != dbLogEntry1.getLogEntryID());
        }
        logEntryMapper.delete(dbLogEntry2);
        logEntryList = logEntryMapper.readAll();
        for (LogEntry someLogEntry : logEntryList) {
            assertTrue(someLogEntry.getLogEntryID() != dbLogEntry2.getLogEntryID());
        }
        logEntryMapper.delete(dbLogEntry3);
        logEntryList = logEntryMapper.readAll();
        for (LogEntry someLogEntry : logEntryList) {
            assertTrue(someLogEntry.getLogEntryID() != dbLogEntry3.getLogEntryID());
        }
    }

    @Override
    public boolean objectsAreEqual(LogEntry logEntry1, LogEntry logEntry2) {
        return (
                logEntry1.getLogEntryDate().equals(logEntry2.getLogEntryDate()) &&
                        logEntry1.getLogEntryHours().equals(logEntry2.getLogEntryHours()) &&
                        logEntry1.getLogEntryComments().equals(logEntry2.getLogEntryComments()) &&
                        logEntry1.getLogEntryLocationName().equals(logEntry2.getLogEntryLocationName()) &&
                        logEntry1.getLogEntryLocationAddress().equals(logEntry2.getLogEntryLocationAddress()) &&
                        logEntry1.getLogEntryLocationCity().equals(logEntry2.getLogEntryLocationCity()) &&
                        logEntry1.getLogEntryLocationState().equals(logEntry2.getLogEntryLocationState()) &&
                        logEntry1.getLogEntryLocationZipCode().equals(logEntry2.getLogEntryLocationZipCode()) &&
                        logEntry1.getLogEntryCompanyName().equals(logEntry2.getLogEntryCompanyName()) &&
                        logEntry1.getLogEntrySupervisorFirstName().equals(logEntry2.getLogEntrySupervisorFirstName()) &&
                        logEntry1.getLogEntrySupervisorLastName().equals(logEntry2.getLogEntrySupervisorLastName()) &&
                        logEntry1.getLogEntrySupervisorDisplayName().equals(logEntry2.getLogEntrySupervisorDisplayName())
        );
    }
}