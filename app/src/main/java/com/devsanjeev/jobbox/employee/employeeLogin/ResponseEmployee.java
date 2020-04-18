package com.devsanjeev.jobbox.employee.employeeLogin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class ResponseEmployee {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("candidate")
        @Expose
        private Candidate candidate;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Candidate getCandidate() {
            return candidate;
        }

        public void setCandidate(Candidate candidate) {
            this.candidate = candidate;
        }

    }