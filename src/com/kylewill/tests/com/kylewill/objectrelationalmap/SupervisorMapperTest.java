package com.kylewill.objectrelationalmap;

import com.kylewill.DatabaseHelper;
import com.kylewill.model.Supervisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class SupervisorMapperTest implements MapperTest<Supervisor> {
    private SupervisorMapper supervisorMapper = new SupervisorMapper();
    private Supervisor jimbo;
    private Supervisor frankie;
    private Supervisor bobby;
    private List<Supervisor> supervisorList;
    private Supervisor dbJimbo;
    private Supervisor dbFrankie;
    private Supervisor dbBobby;

    @Before
    public void populateDatabase() throws Exception {
        DatabaseHelper.deleteDatabase();
        DatabaseHelper.createDatabase();
        jimbo = new Supervisor("Jimbo");
        jimbo.setSupervisorFirstName("Jim");
        jimbo.setSupervisorLastName("Jimmerson");
        frankie = new Supervisor("Frankie");
        frankie.setSupervisorFirstName("Frank");
        frankie.setSupervisorLastName("Frankerson");
        bobby = new Supervisor("Bobby");
        bobby.setSupervisorFirstName("Bob");
        bobby.setSupervisorLastName("Bobberson");
        supervisorMapper.create(jimbo);
        supervisorMapper.create(frankie);
        supervisorMapper.create(bobby);
        // Read the records
        supervisorList = supervisorMapper.readAll();
        dbJimbo = supervisorList.get(0);
        dbFrankie = supervisorList.get(1);
        dbBobby = supervisorList.get(2);
    }

    @After
    public void deleteDatabase() {
        DatabaseHelper.deleteDatabase();
    }

    // Testing to see that the records were read properly also
    // inherently tests to see that the records were created properly.
    // As a result, there is no need for a separate test method to test
    // the create method of the mapper class.

    @Test
    public void readUsingSupervisorId() {
        Supervisor dbJimboRetrievedUsingSupervisorName = supervisorMapper.read(dbJimbo.getSupervisorID());
        Supervisor dbFrankieRetrievedUsingSupervisorName = supervisorMapper.read(dbFrankie.getSupervisorID());
        Supervisor dbBobbyRetrievedUsingSupervisorName = supervisorMapper.read(dbBobby.getSupervisorID());
        assertTrue(objectsAreEqual(dbJimbo, dbJimboRetrievedUsingSupervisorName));
        assertTrue(objectsAreEqual(dbFrankie, dbFrankieRetrievedUsingSupervisorName));
        assertTrue(objectsAreEqual(dbBobby, dbBobbyRetrievedUsingSupervisorName));
    }

    @Test
    public void readUsingSupervisorDisplayName() {
        Supervisor dbJimboRetrievedUsingSupervisorName = supervisorMapper.read(dbJimbo.getSupervisorDisplayName());
        Supervisor dbFrankieRetrievedUsingSupervisorName = supervisorMapper.read(dbFrankie.getSupervisorDisplayName());
        Supervisor dbBobbyRetrievedUsingSupervisorName = supervisorMapper.read(dbBobby.getSupervisorDisplayName());
        assertTrue(objectsAreEqual(dbJimbo, dbJimboRetrievedUsingSupervisorName));
        assertTrue(objectsAreEqual(dbFrankie, dbFrankieRetrievedUsingSupervisorName));
        assertTrue(objectsAreEqual(dbBobby, dbBobbyRetrievedUsingSupervisorName));
    }

    @Test
    public void readAll() {
        // All records are created and read during the Before method.
        // This method only serves to verify that the records were properly read.
        assertTrue(dbJimbo.getSupervisorID() == 1 && objectsAreEqual(dbJimbo, jimbo));
        assertTrue(dbFrankie.getSupervisorID() == 2 && objectsAreEqual(dbFrankie, frankie));
        assertTrue(dbBobby.getSupervisorID() == 3 && objectsAreEqual(dbBobby, bobby));
    }

    @Test
    public void update() {
        // Update the records
        dbJimbo.setSupervisorFirstName("A First Name");
        dbJimbo.setSupervisorLastName("A Last Name");
        dbJimbo.setSupervisorDisplayName("A Display Name");

        dbFrankie.setSupervisorFirstName("B First Name");
        dbFrankie.setSupervisorLastName("B Last Name");
        dbFrankie.setSupervisorDisplayName("B Display Name");

        dbBobby.setSupervisorFirstName("C First Name");
        dbBobby.setSupervisorLastName("C Last Name");
        dbBobby.setSupervisorDisplayName("C Display Name");

        supervisorMapper.update(dbJimbo);
        supervisorMapper.update(dbFrankie);
        supervisorMapper.update(dbBobby);

        // Verify the records were updated
        supervisorList = supervisorMapper.readAll();
        Supervisor updatedJimbo = supervisorList.get(0);
        Supervisor updatedFrankie = supervisorList.get(1);
        Supervisor updatedBobby = supervisorList.get(2);
        assertTrue(objectsAreEqual(dbJimbo, updatedJimbo));
        assertTrue(objectsAreEqual(dbFrankie, updatedFrankie));
        assertTrue(objectsAreEqual(dbBobby, updatedBobby));
    }

    @Test
    public void delete() {
        // Delete the records one by one and verify that they were deleted along the way
        supervisorMapper.delete(dbJimbo);
        supervisorList = supervisorMapper.readAll();
        for (Supervisor someSupervisor : supervisorList) {
            assertTrue(someSupervisor.getSupervisorID() != dbJimbo.getSupervisorID());
        }
        supervisorMapper.delete(dbFrankie);
        supervisorList = supervisorMapper.readAll();
        for (Supervisor someSupervisor : supervisorList) {
            assertTrue(someSupervisor.getSupervisorID() != dbFrankie.getSupervisorID());
        }
        supervisorMapper.delete(dbBobby);
        supervisorList = supervisorMapper.readAll();
        for (Supervisor someSupervisor : supervisorList) {
            assertTrue(someSupervisor.getSupervisorID() != dbBobby.getSupervisorID());
        }
    }

    @Override
    public boolean objectsAreEqual(Supervisor supervisor1, Supervisor supervisor2) {
        return (
                supervisor1.getSupervisorFirstName().equals(supervisor2.getSupervisorFirstName()) &&
                supervisor1.getSupervisorLastName().equals(supervisor2.getSupervisorLastName()) &&
                supervisor1.getSupervisorDisplayName().equals(supervisor2.getSupervisorDisplayName())
        );
    }
}