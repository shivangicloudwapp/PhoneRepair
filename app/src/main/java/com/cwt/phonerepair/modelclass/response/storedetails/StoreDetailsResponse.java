package com.cwt.phonerepair.modelclass.response.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StoreDetailsResponse  implements Serializable {


    public StoreDetailsResponse(Boolean status, StoreDetailsData data, List<StoreDetailsProductModel> product, String message) {
        this.status = status;
        this.data = data;
        this.product = product;
        this.message = message;
    }

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private StoreDetailsData data;
    @SerializedName("product")
    @Expose
    private List<StoreDetailsProductModel> product = null;
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

    public List<StoreDetailsProductModel> getProduct() {
        return product;
    }

    public void setProduct(List<StoreDetailsProductModel> product) {
        this.product = product;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
