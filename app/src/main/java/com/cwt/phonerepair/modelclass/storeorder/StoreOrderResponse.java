package com.cwt.phonerepair.modelclass.storeorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreOrderResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private StoreOrderData data;
    @SerializedName("massage")
    @Expose
    private String massage;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public StoreOrderData getData() {
        return data;
    }

    public void setData(StoreOrderData data) {
        this.data = data;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
