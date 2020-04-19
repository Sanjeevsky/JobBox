package com.devsanjeev.jobbox.employer.newApplication;

import com.devsanjeev.jobbox.employee.newFeeds.Application;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseApplication {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("application")
        @Expose
        private Application application;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Application getApplication() {
            return application;
        }

        public void setApplication(Application application) {
            this.application = application;
        }

    }