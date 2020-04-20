package com.devsanjeev.jobbox.employee.employeeProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeProfileResponse {
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
    @SerializedName("mobile")
    @Expose
    private Long mobile;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("aadharNumber")
    @Expose
    private Long aadharNumber;
    @SerializedName("college")
    @Expose
    private String college;
    @SerializedName("dateOfBirth")
    @Expose
    private Object dateOfBirth;
    @SerializedName("graduationCourse")
    @Expose
    private String graduationCourse;
    @SerializedName("graduationPercentage")
    @Expose
    private Double graduationPercentage;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("passoutYear")
    @Expose
    private Integer passoutYear;
    @SerializedName("percentage10")
    @Expose
    private Double percentage10;
    @SerializedName("percentage12")
    @Expose
    private Double percentage12;

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

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Long getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(Long aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGraduationCourse() {
        return graduationCourse;
    }

    public void setGraduationCourse(String graduationCourse) {
        this.graduationCourse = graduationCourse;
    }

    public Double getGraduationPercentage() {
        return graduationPercentage;
    }

    public void setGraduationPercentage(Double graduationPercentage) {
        this.graduationPercentage = graduationPercentage;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPassoutYear() {
        return passoutYear;
    }

    public void setPassoutYear(Integer passoutYear) {
        this.passoutYear = passoutYear;
    }

    public Double getPercentage10() {
        return percentage10;
    }

    public void setPercentage10(Double percentage10) {
        this.percentage10 = percentage10;
    }

    public Double getPercentage12() {
        return percentage12;
    }

    public void setPercentage12(Double percentage12) {
        this.percentage12 = percentage12;
    }

}

