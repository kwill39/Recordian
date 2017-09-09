package databasemanagement.objectrelationalmap;

import databasemanagement.DatabaseHelper;
import model.Company;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class CompanyMapperTest implements DatabaseItemMapperTest<Company> {
    private CompanyMapper companyMapper = new CompanyMapper();
    private Company snowRemover;
    private Company springRemover;
    private Company wetRemover;
    private Company dbSnowRemover;
    private Company dbSpringRemover;
    private Company dbWetRemover;
    private List<Company> companyList;

    @Before
    public void populateDatabase() {
        DatabaseHelper.deleteDatabase();
        DatabaseHelper.createDatabase();
        // Create the records
        snowRemover = new Company("SnowRemover");
        springRemover = new Company("SpringRemover");
        wetRemover = new Company("WetRemover");
        companyMapper.create(snowRemover);
        companyMapper.create(springRemover);
        companyMapper.create(wetRemover);

        // Read the records
        companyList = companyMapper.readAll();
        dbSnowRemover = companyList.get(0);
        dbSpringRemover = companyList.get(1);
        dbWetRemover = companyList.get(2);
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
    public void readUsingCompanyId() {
        Company dbSnowRemoverRetrievedUsingCompanyID = companyMapper.read(dbSnowRemover.getCompanyID());
        Company dbSpringRemoverRetrievedUsingCompanyID = companyMapper.read(dbSpringRemover.getCompanyID());
        Company dbWetRemoverRetrievedUsingCompanyID = companyMapper.read(dbWetRemover.getCompanyID());
        assertTrue(objectsAreEqual(dbSnowRemover, dbSnowRemoverRetrievedUsingCompanyID));
        assertTrue(objectsAreEqual(dbSpringRemover, dbSpringRemoverRetrievedUsingCompanyID));
        assertTrue(objectsAreEqual(dbWetRemover, dbWetRemoverRetrievedUsingCompanyID));
    }

    @Test
    public void readUsingCompanyName() {
        Company dbSnowRemoverRetrievedUsingCompanyName = companyMapper.read(dbSnowRemover.getCompanyName());
        Company dbSpringRemoverRetrievedUsingCompanyName = companyMapper.read(dbSpringRemover.getCompanyName());
        Company dbWetRemoverRetrievedUsingCompanyName = companyMapper.read(dbWetRemover.getCompanyName());
        assertTrue(objectsAreEqual(dbSnowRemover, dbSnowRemoverRetrievedUsingCompanyName));
        assertTrue(objectsAreEqual(dbSpringRemover, dbSpringRemoverRetrievedUsingCompanyName));
        assertTrue(objectsAreEqual(dbWetRemover, dbWetRemoverRetrievedUsingCompanyName));
    }

    @Test
    public void readAll() {
        // All records are created and read during the Before method.
        // This method only serves to verify that the records were properly read.
        assertTrue(dbSnowRemover.getCompanyID() == 1 && objectsAreEqual(dbSnowRemover, snowRemover));
        assertTrue(dbSpringRemover.getCompanyID() == 2 && objectsAreEqual(dbSpringRemover, springRemover));
        assertTrue(dbWetRemover.getCompanyID() == 3 && objectsAreEqual(dbWetRemover, wetRemover));
    }

    @Test
    public void update() {
        // Update the records
        dbSnowRemover.setCompanyName("Sunnyland");
        dbSpringRemover.setCompanyName("Fallland");
        dbWetRemover.setCompanyName("Dryland");
        companyMapper.update(dbSnowRemover);
        companyMapper.update(dbSpringRemover);
        companyMapper.update(dbWetRemover);

        // Verify the records were updated
        companyList = companyMapper.readAll();
        Company updatedSnowRemover = companyList.get(0);
        Company updatedSpringRemover = companyList.get(1);
        Company updatedWetRemover = companyList.get(2);
        assertTrue(objectsAreEqual(dbSnowRemover, updatedSnowRemover));
        assertTrue(objectsAreEqual(dbSpringRemover, updatedSpringRemover));
        assertTrue(objectsAreEqual(dbWetRemover, updatedWetRemover));
    }

    @Test
    public void delete() {
        // Delete the records one by one and verify that they were deleted along the way
        companyMapper.delete(dbWetRemover);
        companyList = companyMapper.readAll();
        for (Company someCompany : companyList) {
            assertTrue(someCompany.getCompanyID() != dbWetRemover.getCompanyID());
        }
        companyMapper.delete(dbSnowRemover);
        companyList = companyMapper.readAll();
        for (Company someCompany : companyList) {
            assertTrue(someCompany.getCompanyID() != dbSnowRemover.getCompanyID());
        }
        companyMapper.delete(dbSpringRemover);
        companyList = companyMapper.readAll();
        for (Company someCompany : companyList) {
            assertTrue(someCompany.getCompanyID() != dbSpringRemover.getCompanyID());
        }
    }

    @Override
    public boolean objectsAreEqual(Company company1, Company company2) {
        return company1.getCompanyName().equals(company2.getCompanyName());
    }
}