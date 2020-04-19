package com.devsanjeev.jobbox.employer.newApplication;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestApplication {

    @SerializedName("employerId")
    @Expose
    private String employerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("skillsRequired")
    @Expose
    private String skillsRequired;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("experienceRequired")
    @Expose
    private String experienceRequired;
    @SerializedName("applicationDetails")
    @Expose
    private String applicationDetails;

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(String experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public String getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(String applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

}