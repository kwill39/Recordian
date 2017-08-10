package com.kylewill.model;

/**
 * Represents a company which the user works for
 * @author  Kyle
 * @since   Version 2
 */
public class Company implements DatabaseItem{
    private int companyID;
    private String companyName;

    public Company(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
