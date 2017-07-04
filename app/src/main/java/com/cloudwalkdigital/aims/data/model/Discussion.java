package com.cloudwalkdigital.aims.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alleoindong on 7/4/17.
 */

public class Discussion {
    protected Integer id;
    protected JobOrder jobOrder;
    protected User user;
    protected String message;
    @SerializedName("created_at")
    protected String createdAt;

    public Integer getId() {
        return id;
    }

    public JobOrder getJobOrder() {
        return jobOrder;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
