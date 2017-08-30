package model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a supervisor whom the user works under the supervision of
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
public class Supervisor implements DatabaseItem{
    private int supervisorID;
    private SimpleStringProperty supervisorFirstName = new SimpleStringProperty();
    private SimpleStringProperty supervisorLastName = new SimpleStringProperty();
    /**
     * The nickname of the supervisor that
     * gets displayed to the user in a view
     */
    private SimpleStringProperty supervisorDisplayName = new SimpleStringProperty();

    public Supervisor(String supervisorDisplayName) {
        this.supervisorDisplayName.set(supervisorDisplayName);
    }

    public int getSupervisorID() {
        return supervisorID;
    }
    public void setSupervisorID(int supervisorID) {
        this.supervisorID = supervisorID;
    }

    public SimpleStringProperty supervisorFirstNameProperty() {
        return supervisorFirstName;
    }
    public String getSupervisorFirstName() {
        return supervisorFirstName.get();
    }
    public void setSupervisorFirstName(String supervisorFirstName) {
        this.supervisorFirstName.set(supervisorFirstName);
    }

    public SimpleStringProperty supervisorLastNameProperty() {
        return supervisorLastName;
    }
    public String getSupervisorLastName() {
        return supervisorLastName.get();
    }
    public void setSupervisorLastName(String supervisorLastName) {
        this.supervisorLastName.set(supervisorLastName);
    }

    public SimpleStringProperty supervisorDisplayNameProperty() {
        return supervisorDisplayName;
    }
    public String getSupervisorDisplayName() {
        return supervisorDisplayName.get();
    }
    public void setSupervisorDisplayName(String supervisorDisplayName) {
        this.supervisorDisplayName.set(supervisorDisplayName);
    }
}