package com.cloudwalkdigital.aims.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alleoindong on 6/28/17.
 */

public class JobOrder {
    private Integer id;
    @SerializedName("project_name")
    private String projectName;
    @SerializedName("project_type")
    private String projectType;
    private String deadline;
    @SerializedName("job_order_no")
    private String jobOrderNo;
    private String status;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("pre_event")
    private String preEvent;
    @SerializedName("post_event")
    private String postEvent;
    @SerializedName("event_proper")
    private String eventProper;
    private List<Discussion> discussions;
    private List<Mom> moms;
    private List<Schedule> schedules;

    public JobOrder() {
    }

    public JobOrder(String projectName, String deadline, String jobOrderNo) {
        this.projectName = projectName;
        this.deadline = deadline;
        this.jobOrderNo = jobOrderNo;
    }

    public JobOrder(Integer id, String projectName, String projectType, String deadline,
                    String jobOrderNo, String status, String startDate, String endDate,
                    String preEvent, String postEvent, String eventProper,
                    List<Discussion> discussions, List<Mom> moms, List<Schedule> schedules) {
        this.id = id;
        this.projectName = projectName;
        this.projectType = projectType;
        this.deadline = deadline;
        this.jobOrderNo = jobOrderNo;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.preEvent = preEvent;
        this.postEvent = postEvent;
        this.eventProper = eventProper;
        this.discussions = discussions;
        this.moms = moms;
        this.schedules = schedules;
    }

    public String getProjectName() {
        return this.projectName;
    }
    public String getDeadline() {
        return this.deadline;
    }
    public String getJobOrderNo() {
        return this.jobOrderNo;
    }

    public Integer getId() {
        return id;
    }

    public String getProjectType() {
        return projectType;
    }

    public String getStatus() {
        return status;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getPreEvent() {
        return preEvent;
    }

    public String getPostEvent() {
        return postEvent;
    }

    public String getEventProper() {
        return eventProper;
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public List<Mom> getMoms() {
        return moms;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}
