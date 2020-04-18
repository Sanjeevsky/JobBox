package com.devsanjeev.jobbox.employee.newFeeds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Applications {
    @SerializedName("_id")
    @Expose
    private String applicationID;
    @SerializedName("employeeID")
    @Expose
    private List<EmployeeID> employeeID = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("employerID")
    @Expose
    private String employerID;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getapplicationID() {
        return applicationID;
    }

    public void setapplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public List<EmployeeID> getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(List<EmployeeID> employeeID) {
        this.employeeID = employeeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
