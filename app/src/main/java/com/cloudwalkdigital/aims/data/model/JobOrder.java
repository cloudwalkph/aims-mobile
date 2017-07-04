package com.cloudwalkdigital.aims.data.model;

/**
 * Created by alleoindong on 6/28/17.
 */

public class JobOrder {
    public final String projectName;
    public final String deadline;
    public final String jobOrderNo;

    public JobOrder(String projectName, String deadline, String jobOrderNo) {
        this.projectName = projectName;
        this.deadline = deadline;
        this.jobOrderNo = jobOrderNo;
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


}
