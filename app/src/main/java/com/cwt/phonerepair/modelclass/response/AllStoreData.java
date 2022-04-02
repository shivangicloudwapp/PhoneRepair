package com.cwt.phonerepair.modelclass.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllStoreData {



    @SerializedName("store")
    @Expose
    private List<AllStoreModel> store = null;

    public List<AllStoreModel> getStore() {
        return store;
    }

    public void setStore(List<AllStoreModel> store) {
        this.store = store;
    }
}
