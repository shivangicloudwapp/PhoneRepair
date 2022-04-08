package com.cwt.phonerepair.modelclass.response.getproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductReponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private GetProductData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public GetProductData getData() {
        return data;
    }

    public void setData(GetProductData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
