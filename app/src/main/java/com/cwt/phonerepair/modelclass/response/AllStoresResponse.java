package com.cwt.phonerepair.modelclass.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllStoresResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private AllStoreData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public AllStoreData getData() {
        return data;
    }

    public void setData(AllStoreData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}