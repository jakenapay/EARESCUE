package com.example.it3b_partialapps_grp1;

import java.sql.Timestamp;

public class activityLog {
    private String logtime;
    private String outtime;
    private String status;
    private String uid;
    private String username;

    public activityLog(String logtime, String outtime, String status, String uid, String username) {
        this.logtime = logtime;
        this.outtime = outtime;
        this.status = status;
        this.uid = uid;
        this.username = username;
    }

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // No-argument constructor (required for Firebase)
}