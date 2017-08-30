package model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a company which the user works for
 * @author  Kyle Williams
 * @since   Version 2
 */
public class Company implements DatabaseItem{
    private int companyID;
    private SimpleStringProperty companyName = new SimpleStringProperty();

    public Company(String companyName) {
        this.companyName.set(companyName);
    }

    public int getCompanyID() {
        return companyID;
    }
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }
    public String getCompanyName() {
        return companyName.get();
    }
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
}