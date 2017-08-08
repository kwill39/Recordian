package com.kylewill.model;

public class Supervisor{
    private int supervisorID;
    private String supervisorFirstName;
    private String supervisorLastName;
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
