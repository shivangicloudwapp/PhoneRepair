package com.cwt.phonerepair.modelclass.response.AddProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductManagementResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ProdcutManagementData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ProdcutManagementData getData() {
        return data;
    }

    public void setData(ProdcutManagementData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
