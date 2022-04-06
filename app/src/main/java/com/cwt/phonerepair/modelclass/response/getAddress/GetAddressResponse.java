package com.cwt.phonerepair.modelclass.response.getAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddressResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private GetAddressData data;
    @SerializedName("massage")
    @Expose
    private String massage;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public GetAddressData getData() {
        return data;
    }

    public void setData(GetAddressData data) {
        this.data = data;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

}
