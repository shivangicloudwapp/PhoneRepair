package com.cwt.phonerepair.modelclass.response.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeStoreModel {

    public HomeStoreModel(Integer id, String storeName, String registrationNo, String address, String aboutStore, String storeImage, String createdAt, String updatedAt) {
        this.id = id;
        this.storeName = storeName;
        this.registrationNo = registrationNo;
        this.address = address;
        this.aboutStore = aboutStore;
        this.storeImage = storeImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("registration_no")
    @Expose
    private String registrationNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("about_store")
    @Expose
    private String aboutStore;
    @SerializedName("store_image")
    @Expose
    private String storeImage;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAboutStore() {
        return aboutStore;
    }

    public void setAboutStore(String aboutStore) {
        this.aboutStore = aboutStore;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
