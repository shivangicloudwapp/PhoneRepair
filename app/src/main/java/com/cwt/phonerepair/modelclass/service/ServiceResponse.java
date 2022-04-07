package com.cwt.phonerepair.modelclass.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponse {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ServicesData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ServicesData getData() {
        return data;
    }

    public void setData(ServicesData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
