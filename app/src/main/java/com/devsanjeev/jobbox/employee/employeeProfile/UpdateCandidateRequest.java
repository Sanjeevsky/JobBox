package com.devsanjeev.jobbox.employee.employeeProfile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCandidateRequest {

    @SerializedName("skillSets")
    @Expose
    private List<String> skillSets = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("college")
    @Expose
    private String college;
    @SerializedName("passoutYear")
    @Expose
    private Integer passoutYear;
    @SerializedName("percentage10")
    @Expose
    private Float percentage10;
    @SerializedName("percentage12")
    @Expose
    private Float percentage12;
    @SerializedName("mobile")
    @Expose
    private Long mobile;
    @SerializedName("graduationCourse")
    @Expose
    private String graduationCourse;
    @SerializedName("graduationPercentage")
    @Expose
    private Float graduationPercentage;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("aadharNumber")
    @Expose
    private Long aadharNumber;

    public List<String> getSkillSets() {
        return skillSets;
    }

    public void setSkillSets(List<String> skillSets) {
        this.skillSets = skillSets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getPassoutYear() {
        return passoutYear;
    }

    public void setPassoutYear(Integer passoutYear) {
        this.passoutYear = passoutYear;
    }

    public Float getPercentage10() {
        return percentage10;
    }

    public void setPercentage10(Float percentage10) {
        this.percentage10 = percentage10;
    }

    public Float getPercentage12() {
        return percentage12;
    }

    public void setPercentage12(Float percentage12) {
        this.percentage12 = percentage12;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getGraduationCourse() {
        return graduationCourse;
    }

    public void setGraduationCourse(String graduationCourse) {
        this.graduationCourse = graduationCourse;
    }

    public Float getGraduationPercentage() {
        return graduationPercentage;
    }

    public void setGraduationPercentage(Float graduationPercentage) {
        this.graduationPercentage = graduationPercentage;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(Long aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

}