package com.devsanjeev.jobbox.employer.createdApplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeCandidateRequest {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("_id")
    @Expose
    private String applicationId;
    @SerializedName("employeeId")
    @Expose
    private String employeeId;
    @SerializedName("status")
    @Expose
    private String status;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}