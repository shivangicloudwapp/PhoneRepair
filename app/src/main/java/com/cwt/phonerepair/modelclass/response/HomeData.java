package com.cwt.phonerepair.modelclass.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeData {

    @SerializedName("banner")
    @Expose
    private List<HomeBannerModel> banner = null;
    @SerializedName("store")
    @Expose
    private List<HomeStoreModel> store = null;

    public List<HomeBannerModel> getBanner() {
        return banner;
    }

    public void setBanner(List<HomeBannerModel> banner) {
        this.banner = banner;
    }

    public List<HomeStoreModel> getStore() {
        return store;
    }

    public void setStore(List<HomeStoreModel> store) {
        this.store = store;
    }
}
