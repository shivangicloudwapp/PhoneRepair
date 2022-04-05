package com.cwt.phonerepair.modelclass.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreDetailsResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private StoreDetailsData data;
    @SerializedName("product")
    @Expose
    private List<StoreDetailsProduct> product = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public StoreDetailsData getData() {
        return data;
    }

    public void setData(StoreDetailsData data) {
        this.data = data;
    }

    public List<StoreDetailsProduct> getProduct() {
        return product;
    }

    public void setProduct(List<StoreDetailsProduct> product) {
        this.product = product;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
