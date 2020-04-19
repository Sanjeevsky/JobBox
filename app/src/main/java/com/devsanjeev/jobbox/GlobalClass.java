package com.devsanjeev.jobbox;

import android.app.Application;

import com.devsanjeev.jobbox.employee.employeeLogin.Candidate;
import com.devsanjeev.jobbox.employer.employerLogin.Employer;

public class GlobalClass extends Application {
    private String token;
    private Employer employer;
    private Candidate candidate;

    public GlobalClass() {
    }

    public GlobalClass(String token, Employer employer) {
        this.token = token;
        this.employer = employer;
    }

    public GlobalClass(String token, Candidate candidate) {
        this.token = token;
        this.candidate = candidate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
