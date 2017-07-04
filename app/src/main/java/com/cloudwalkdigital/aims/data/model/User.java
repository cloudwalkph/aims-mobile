package com.cloudwalkdigital.aims.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alleoindong on 7/4/17.
 */

public class User {
    protected Integer id;
    protected String email;
    protected Department department;
    protected Role role;
    protected Profile profile;
    @SerializedName("api_token")
    protected String apiToken;

    public User() {
    }

    public User(Integer id, String email, Department department,
                Role role, Profile profile, String apiToken) {
        this.id = id;
        this.email = email;
        this.department = department;
        this.role = role;
        this.profile = profile;
        this.apiToken = apiToken;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Department getDepartment() {
        return department;
    }

    public Role getRole() {
        return role;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getApiToken() {
        return apiToken;
    }
}
