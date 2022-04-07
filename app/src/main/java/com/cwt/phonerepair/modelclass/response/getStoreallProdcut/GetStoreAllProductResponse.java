package com.cwt.phonerepair.modelclass.response.getStoreallProdcut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStoreAllProductResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private GetStoreAllProductData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public GetStoreAllProductData getData() {
        return data;
    }

    public void setData(GetStoreAllProductData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
