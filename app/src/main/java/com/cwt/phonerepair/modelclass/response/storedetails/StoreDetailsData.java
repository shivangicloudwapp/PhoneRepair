package com.cwt.phonerepair.modelclass.response.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreDetailsData {


    @SerializedName("store")
    @Expose
    private StoreDetailsModel store;

    public StoreDetailsModel getStore() {
        return store;
    }

    public void setStore(StoreDetailsModel store) {
        this.store = store;
    }

}
