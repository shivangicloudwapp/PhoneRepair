package com.cwt.phonerepair.modelclass.response.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private HomeData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public HomeData getData() {
        return data;
    }

    public void setData(HomeData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
