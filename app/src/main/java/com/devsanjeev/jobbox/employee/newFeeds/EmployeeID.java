package com.devsanjeev.jobbox.employee.newFeeds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeID {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("id")
    @Expose
    private String applicantID;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getapplicantID() {
        return applicantID;
    }

    public void setapplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}