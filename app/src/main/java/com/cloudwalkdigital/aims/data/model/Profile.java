package com.cloudwalkdigital.aims.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alleoindong on 7/4/17.
 */

public class Profile extends RealmObject {
    @PrimaryKey
    protected Integer id;
    @SerializedName("first_name")
    protected String firstName;
    @SerializedName("last_name")
    protected String lastName;

    public Profile() {
    }

    public Profile(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
