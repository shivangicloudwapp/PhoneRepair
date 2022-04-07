package com.cwt.phonerepair.modelclass.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServicesData {
    @SerializedName("pending")
    @Expose
    private List<ServicePendingModel> pending = null;
    @SerializedName("Rejected")
    @Expose
    private List<Object> rejected = null;
    @SerializedName("Accepted")
    @Expose
    private List<Object> accepted = null;
    @SerializedName("Completed")
    @Expose
    private List<ServiceCompleteModel> completed = null;

    public List<ServicePendingModel> getPending() {
        return pending;
    }

    public void setPending(List<ServicePendingModel> pending) {
        this.pending = pending;
    }

    public List<Object> getRejected() {
        return rejected;
    }

    public void setRejected(List<Object> rejected) {
        this.rejected = rejected;
    }

    public List<Object> getAccepted() {
        return accepted;
    }

    public void setAccepted(List<Object> accepted) {
        this.accepted = accepted;
    }

    public List<ServiceCompleteModel> getCompleted() {
        return completed;
    }

    public void setCompleted(List<ServiceCompleteModel> completed) {
        this.completed = completed;
    }

}
