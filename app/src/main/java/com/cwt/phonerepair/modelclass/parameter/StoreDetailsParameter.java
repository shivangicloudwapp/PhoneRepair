package com.cwt.phonerepair.modelclass.parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreDetailsParameter {

    @SerializedName("store_id")
    @Expose
    private Integer storeId;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
