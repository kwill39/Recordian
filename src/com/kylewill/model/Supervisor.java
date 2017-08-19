package com.kylewill.model;

/**
 * Represents a supervisor whom the user works under the supervision of
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
public class Supervisor implements DatabaseItem{
    private int supervisorID;
    private String supervisorFirstName;
    private String supervisorLastName;
    /**
     * The nickname of the supervisor that
     * gets displayed to the user in a view
     */
    private String supervisorDisplayName;

    public Supervisor(String supervisorDisplayName) {
        this.supervisorDisplayName = supervisorDisplayName;
    }

    public int getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(int supervisorID) {
        this.supervisorID = supervisorID;
    }

    public String getSupervisorFirstName() {
        return supervisorFirstName;
    }

    public void setSupervisorFirstName(String supervisorFirstName) {
        this.supervisorFirstName = supervisorFirstName;
    }

    public String getSupervisorLastName() {
        return supervisorLastName;
    }

    public void setSupervisorLastName(String supervisorLastName) {
        this.supervisorLastName = supervisorLastName;
    }

    public String getSupervisorDisplayName() {
        return supervisorDisplayName;
    }

    public void setSupervisorDisplayName(String supervisorDisplayName) {
        this.supervisorDisplayName = supervisorDisplayName;
    }
}
