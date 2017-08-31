package model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a log entry that the user submitted
 *
 * @author  Kyle Williams
 * @since   Version 3
 */
public class LogEntry implements DatabaseItem {
    private int logEntryID;
    private SimpleStringProperty logEntryDate = new SimpleStringProperty();
    private SimpleStringProperty logEntryHours = new SimpleStringProperty();
    private SimpleStringProperty logEntryComments = new SimpleStringProperty();
    private SimpleStringProperty logEntryLocationName = new SimpleStringProperty();
    private SimpleStringProperty logEntryLocationAddress = new SimpleStringProperty();
    private SimpleStringProperty logEntryLocationCity = new SimpleStringProperty();
    private SimpleStringProperty logEntryLocationState = new SimpleStringProperty();
    private SimpleStringProperty logEntryLocationZipCode = new SimpleStringProperty();
    private SimpleStringProperty logEntryCompanyName = new SimpleStringProperty();
    private SimpleStringProperty logEntrySupervisorDisplayName = new SimpleStringProperty();
    private SimpleStringProperty logEntrySupervisorFirstName = new SimpleStringProperty();
    private SimpleStringProperty logEntrySupervisorLastName = new SimpleStringProperty();

    public LogEntry(String logEntryDate, String logEntryHours) {
        this.logEntryDate.set(logEntryDate);
        this.logEntryHours.set(logEntryHours);
    }

    public int getLogEntryID() {
        return logEntryID;
    }
    public void setLogEntryID(int logEntryID) {
        this.logEntryID = logEntryID;
    }

    public String getLogEntryDate() {
        return logEntryDate.get();
    }
    public SimpleStringProperty logEntryDateProperty() {
        return logEntryDate;
    }
    public void setLogEntryDate(String logEntryDate) {
        this.logEntryDate.set(logEntryDate);
    }

    public String getLogEntryHours() {
        return logEntryHours.get();
    }
    public SimpleStringProperty logEntryHoursProperty() {
        return logEntryHours;
    }
    public void setLogEntryHours(String logEntryHours) {
        this.logEntryHours.set(logEntryHours);
    }

    public String getLogEntryComments() {
        return logEntryComments.get();
    }
    public SimpleStringProperty logEntryCommentsProperty() {
        return logEntryComments;
    }
    public void setLogEntryComments(String logEntryComments) {
        this.logEntryComments.set(logEntryComments);
    }

    public String getLogEntryLocationName() {
        return logEntryLocationName.get();
    }
    public SimpleStringProperty logEntryLocationNameProperty() {
        return logEntryLocationName;
    }
    public void setLogEntryLocationName(String logEntryLocationName) {
        this.logEntryLocationName.set(logEntryLocationName);
    }

    public String getLogEntryLocationAddress() {
        return logEntryLocationAddress.get();
    }
    public SimpleStringProperty logEntryLocationAddressProperty() {
        return logEntryLocationAddress;
    }
    public void setLogEntryLocationAddress(String logEntryLocationAddress) {
        this.logEntryLocationAddress.set(logEntryLocationAddress);
    }

    public String getLogEntryLocationCity() {
        return logEntryLocationCity.get();
    }
    public SimpleStringProperty logEntryLocationCityProperty() {
        return logEntryLocationCity;
    }
    public void setLogEntryLocationCity(String logEntryLocationCity) {
        this.logEntryLocationCity.set(logEntryLocationCity);
    }

    public String getLogEntryLocationState() {
        return logEntryLocationState.get();
    }
    public SimpleStringProperty logEntryLocationStateProperty() {
        return logEntryLocationState;
    }
    public void setLogEntryLocationState(String logEntryLocationState) {
        this.logEntryLocationState.set(logEntryLocationState);
    }

    public String getLogEntryLocationZipCode() {
        return logEntryLocationZipCode.get();
    }
    public SimpleStringProperty logEntryLocationZipCodeProperty() {
        return logEntryLocationZipCode;
    }
    public void setLogEntryLocationZipCode(String logEntryLocationZipCode) {
        this.logEntryLocationZipCode.set(logEntryLocationZipCode);
    }

    public String getLogEntryCompanyName() {
        return logEntryCompanyName.get();
    }
    public SimpleStringProperty logEntryCompanyNameProperty() {
        return logEntryCompanyName;
    }
    public void setLogEntryCompanyName(String logEntryCompanyName) {
        this.logEntryCompanyName.set(logEntryCompanyName);
    }

    public String getLogEntrySupervisorDisplayName() {
        return logEntrySupervisorDisplayName.get();
    }
    public SimpleStringProperty logEntrySupervisorDisplayNameProperty() {
        return logEntrySupervisorDisplayName;
    }
    public void setLogEntrySupervisorDisplayName(String logEntrySupervisorDisplayName) {
        this.logEntrySupervisorDisplayName.set(logEntrySupervisorDisplayName);
    }

    public String getLogEntrySupervisorFirstName() {
        return logEntrySupervisorFirstName.get();
    }
    public SimpleStringProperty logEntrySupervisorFirstNameProperty() {
        return logEntrySupervisorFirstName;
    }
    public void setLogEntrySupervisorFirstName(String logEntrySupervisorFirstName) {
        this.logEntrySupervisorFirstName.set(logEntrySupervisorFirstName);
    }

    public String getLogEntrySupervisorLastName() {
        return logEntrySupervisorLastName.get();
    }
    public SimpleStringProperty logEntrySupervisorLastNameProperty() {
        return logEntrySupervisorLastName;
    }
    public void setLogEntrySupervisorLastName(String logEntrySupervisorLastName) {
        this.logEntrySupervisorLastName.set(logEntrySupervisorLastName);
    }
}