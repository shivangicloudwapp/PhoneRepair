package com.cwt.phonerepair.modelclass.storeorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreOrderData {

    @SerializedName("order")
    @Expose
    private List<StoreOrderModel> order = null;

    public List<StoreOrderModel> getOrder() {
        return order;
    }

    public void setOrder(List<StoreOrderModel> order) {
        this.order = order;
    }
}
