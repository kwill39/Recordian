package model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a location where the user works
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
public class Location implements DatabaseItem{

    private int locationID;
    private SimpleStringProperty locationName = new SimpleStringProperty();
    private SimpleStringProperty locationAddress = new SimpleStringProperty();
    private SimpleStringProperty locationCity = new SimpleStringProperty();
    private SimpleStringProperty locationState = new SimpleStringProperty();
    private SimpleStringProperty locationZipCode = new SimpleStringProperty();

    public Location(String locationName) {
        this.locationName.set(locationName);
    }

    public int getLocationID() {
        return locationID;
    }
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public SimpleStringProperty locationNameProperty() {
        return locationName;
    }
    public String getLocationName() {
        return locationName.get();
    }
    public void setLocationName(String locationName) {
        this.locationName.set(locationName);
    }

    public SimpleStringProperty locationAddressProperty() {
        return locationAddress;
    }
    public String getLocationAddress() {
        return locationAddress.get();
    }
    public void setLocationAddress(String locationAddress) {
        this.locationAddress.set(locationAddress);
    }

    public SimpleStringProperty locationCityProperty() {
        return locationCity;
    }
    public String getLocationCity() {
        return locationCity.get();
    }
    public void setLocationCity(String locationCity) {
        this.locationCity.set(locationCity);
    }

    public SimpleStringProperty locationStateProperty() {
        return locationState;
    }
    public String getLocationState() {
        return locationState.get();
    }
    public void setLocationState(String locationState) {
        this.locationState.set(locationState);
    }

    public SimpleStringProperty locationZipCodeProperty() {
        return locationZipCode;
    }
    public String getLocationZipCode() {
        return locationZipCode.get();
    }
    public void setLocationZipCode(String locationZipCode) {
        this.locationZipCode.set(locationZipCode);
    }
}