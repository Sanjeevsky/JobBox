package com.devsanjeev.jobbox.employer.createdApplication;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeApplicationResponse {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("employerID")
    @Expose
    private String employerID;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("applicationDetails")
    @Expose
    private String applicationDetails;
    @SerializedName("skillsRequired")
    @Expose
    private String skillsRequired;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("experienceRequired")
    @Expose
    private String experienceRequired;
    @SerializedName("employeeID")
    @Expose
    private List<Object> employeeID = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(String applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(String experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public List<Object> getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(List<Object> employeeID) {
        this.employeeID = employeeID;
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