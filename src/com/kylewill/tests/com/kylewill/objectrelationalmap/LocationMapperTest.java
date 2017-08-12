package com.kylewill.objectrelationalmap;

import com.kylewill.DatabaseHelper;
import com.kylewill.model.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class LocationMapperTest implements MapperTest<Location> {
    private LocationMapper locationMapper = new LocationMapper();
    private Location snowland;
    private Location springland;
    private Location wetland;
    private List<Location> locationList;
    private Location dbSnowland;
    private Location dbSpringland;
    private Location dbWetland;


    @Before
    public void populateDatabase(){
        DatabaseHelper.deleteDatabase();
        DatabaseHelper.createDatabase();
        // Create the records
        snowland = new Location("Snowland");
        snowland.setLocationAddress("188 Winter Blvd");
        snowland.setLocationCity("Snow City");
        snowland.setLocationState("North Pole");
        snowland.setLocationZipCode("11111");
        springland = new Location("Springland");
        springland.setLocationAddress("188 Meadow Dr");
        springland.setLocationCity("Rose Garden");
        springland.setLocationState("New Flower");
        springland.setLocationZipCode("22222");
        wetland = new Location("Wetland");
        wetland.setLocationAddress("188 Rainy St");
        wetland.setLocationCity("Atlantis");
        wetland.setLocationState("Under Da Sea");
        wetland.setLocationZipCode("33333");
        locationMapper.create(snowland);
        locationMapper.create(springland);
        locationMapper.create(wetland);

        // Read the records
        locationList = locationMapper.readAll();
        dbSnowland = locationList.get(0);
        dbSpringland = locationList.get(1);
        dbWetland = locationList.get(2);
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
    public void readUsingLocationId() {
        Location dbSnowlandRetrievedUsingLocationName = locationMapper.read(dbSnowland.getLocationID());
        Location dbSpringlandRetrievedUsingLocationName = locationMapper.read(dbSpringland.getLocationID());
        Location dbWetlandRetrievedUsingLocationName = locationMapper.read(dbWetland.getLocationID());
        assertTrue(objectsAreEqual(dbSnowland, dbSnowlandRetrievedUsingLocationName));
        assertTrue(objectsAreEqual(dbSpringland, dbSpringlandRetrievedUsingLocationName));
        assertTrue(objectsAreEqual(dbWetland, dbWetlandRetrievedUsingLocationName));
    }

    @Test
    public void readUsingLocationName() {
        Location dbSnowlandRetrievedUsingLocationName = locationMapper.read(dbSnowland.getLocationName());
        Location dbSpringlandRetrievedUsingLocationName = locationMapper.read(dbSpringland.getLocationName());
        Location dbWetlandRetrievedUsingLocationName = locationMapper.read(dbWetland.getLocationName());
        assertTrue(objectsAreEqual(dbSnowland, dbSnowlandRetrievedUsingLocationName));
        assertTrue(objectsAreEqual(dbSpringland, dbSpringlandRetrievedUsingLocationName));
        assertTrue(objectsAreEqual(dbWetland, dbWetlandRetrievedUsingLocationName));
    }

    @Test
    public void readAll() {
        // All records are created and read during the Before method.
        // This method only serves to verify that the records were properly read.
        assertTrue(dbSnowland.getLocationID() == 1 && objectsAreEqual(dbSnowland, snowland));
        assertTrue(dbSpringland.getLocationID() == 2 && objectsAreEqual(dbSpringland, springland));
        assertTrue(dbWetland.getLocationID() == 3 && objectsAreEqual(dbWetland, wetland));
    }

    @Test
    public void update(){
        // Update the records
        dbSnowland.setLocationName("A Name");
        dbSnowland.setLocationAddress("A Street");
        dbSnowland.setLocationCity("A City");
        dbSnowland.setLocationState("A State");
        dbSnowland.setLocationZipCode("A Zip Code");
        locationMapper.update(dbSnowland);

        dbSpringland.setLocationName("B Name");
        dbSpringland.setLocationAddress("B Street");
        dbSpringland.setLocationCity("B City");
        dbSpringland.setLocationState("B State");
        dbSpringland.setLocationZipCode("B Zip Code");
        locationMapper.update(dbSpringland);

        dbWetland.setLocationName("C Name");
        dbWetland.setLocationAddress("C Street");
        dbWetland.setLocationCity("C City");
        dbWetland.setLocationState("C State");
        dbWetland.setLocationZipCode("C Zip Code");
        locationMapper.update(dbWetland);

        // Verify the records were updated
        locationList = locationMapper.readAll();
        Location updatedSnowland = locationList.get(0);
        Location updatedSpringland = locationList.get(1);
        Location updatedWetland = locationList.get(2);

        assertTrue(objectsAreEqual(dbSnowland, updatedSnowland));
        assertTrue(objectsAreEqual(dbSpringland, updatedSpringland));
        assertTrue(objectsAreEqual(dbWetland, updatedWetland));
    }

    @Test
    public void delete(){
        // Delete the records one by one and verify that they were deleted along the way
        locationMapper.delete(dbWetland);
        locationList = locationMapper.readAll();
        for (Location someLocation : locationList) {
            assertTrue(someLocation.getLocationID() != dbWetland.getLocationID());
        }
        locationMapper.delete(dbSnowland);
        locationList = locationMapper.readAll();
        for (Location someLocation : locationList) {
            assertTrue(someLocation.getLocationID() != dbSnowland.getLocationID());
        }
        locationMapper.delete(dbSpringland);
        locationList = locationMapper.readAll();
        for (Location someLocation : locationList) {
            assertTrue(someLocation.getLocationID() != dbSpringland.getLocationID());
        }
    }

    @Override
    public boolean objectsAreEqual(Location location1, Location location2) {
        return (
                location1.getLocationName().equals(location2.getLocationName()) &&
                location1.getLocationAddress().equals(location2.getLocationAddress()) &&
                location1.getLocationCity().equals(location2.getLocationCity()) &&
                location1.getLocationState().equals(location2.getLocationState()) &&
                location1.getLocationZipCode().equals(location2.getLocationZipCode())
        );
    }
}