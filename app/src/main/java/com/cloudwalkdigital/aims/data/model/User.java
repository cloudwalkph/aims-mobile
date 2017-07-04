package com.cloudwalkdigital.aims.data.model;

/**
 * Created by alleoindong on 6/28/17.
 */

public class User {
    public final String firstName;
    public final String lastName;
    public final String department;

    public User(String firstName, String lastName, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getDepartment() {
        return this.department;
    }

}
