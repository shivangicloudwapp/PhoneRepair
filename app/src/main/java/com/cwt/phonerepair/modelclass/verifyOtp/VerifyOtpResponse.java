package com.cwt.phonerepair.modelclass.verifyOtp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private VerifyOtpData data;
    @SerializedName("massage")
    @Expose
    private String massage;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public VerifyOtpData getData() {
        return data;
    }

    public void setData(VerifyOtpData data) {
        this.data = data;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
