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

    public String getLocationName() {
        return locationName.get();
    }
    public SimpleStringProperty locationNameProperty() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName.set(locationName);
    }

    public String getLocationAddress() {
        return locationAddress.get();
    }
    public SimpleStringProperty locationAddressProperty() {
        return locationAddress;
    }
    public void setLocationAddress(String locationAddress) {
        this.locationAddress.set(locationAddress);
    }

    public String getLocationCity() {
        return locationCity.get();
    }
    public SimpleStringProperty locationCityProperty() {
        return locationCity;
    }
    public void setLocationCity(String locationCity) {
        this.locationCity.set(locationCity);
    }

    public String getLocationState() {
        return locationState.get();
    }
    public SimpleStringProperty locationStateProperty() {
        return locationState;
    }
    public void setLocationState(String locationState) {
        this.locationState.set(locationState);
    }

    public String getLocationZipCode() {
        return locationZipCode.get();
    }
    public SimpleStringProperty locationZipCodeProperty() {
        return locationZipCode;
    }
    public void setLocationZipCode(String locationZipCode) {
        this.locationZipCode.set(locationZipCode);
    }
}