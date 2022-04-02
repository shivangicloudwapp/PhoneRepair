package com.cwt.phonerepair.modelclass.parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpParameter {


    public VerifyOtpParameter(Integer userId, String otp) {
        this.userId = userId;
        this.otp = otp;
    }

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("otp")
    @Expose
    private String otp;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
