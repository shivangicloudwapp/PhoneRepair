package com.cwt.phonerepair.modelclass.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicePendingModel implements Serializable {



    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
