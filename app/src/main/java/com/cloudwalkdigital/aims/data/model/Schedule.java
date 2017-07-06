package com.cloudwalkdigital.aims.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alleoindong on 7/5/17.
 */

public class Schedule extends RealmObject {
    @PrimaryKey
    private Integer id;
    private Venue venue;
    private String remarks;
    @SerializedName("jo_datetime")
    private String joDatetime;
    private String status;

    public Schedule() {
    }

    public Schedule(Integer id, Venue venue, String remarks,
                    String joDatetime, String status) {
        this.id = id;
        this.venue = venue;
        this.remarks = remarks;
        this.joDatetime = joDatetime;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Venue getVenue() {
        return venue;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getJoDatetime() {
        return joDatetime;
    }

    public String getStatus() {
        return status;
    }
}
