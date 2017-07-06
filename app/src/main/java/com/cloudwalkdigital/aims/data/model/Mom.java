package com.cloudwalkdigital.aims.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alleoindong on 7/5/17.
 */

public class Mom extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String agenda;
    @SerializedName("date_and_time")
    private String dateAndTime;
    private String location;
    private String attendees;
    @SerializedName("campaign_overview")
    private String campaignOverview;
    @SerializedName("activations_flow")
    private String activationsFlow;
    @SerializedName("next_step_deliverables")
    private String nextStepDeliverables;
    @SerializedName("other_details")
    private String otherDetails;

    public Mom() {
    }

    public Mom(Integer id, String agenda, String dateAndTime, String location, String attendees,
               String campaignOverview, String activationsFlow, String nextStepDeliverables,
               String otherDetails) {
        this.id = id;
        this.agenda = agenda;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.attendees = attendees;
        this.campaignOverview = campaignOverview;
        this.activationsFlow = activationsFlow;
        this.nextStepDeliverables = nextStepDeliverables;
        this.otherDetails = otherDetails;
    }

    public Integer getId() {
        return id;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public String getAttendees() {
        return attendees;
    }

    public String getCampaignOverview() {
        return campaignOverview;
    }

    public String getActivationsFlow() {
        return activationsFlow;
    }

    public String getNextStepDeliverables() {
        return nextStepDeliverables;
    }

    public String getOtherDetails() {
        return otherDetails;
    }
}
