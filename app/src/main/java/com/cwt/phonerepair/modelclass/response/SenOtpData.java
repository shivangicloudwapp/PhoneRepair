package com.cwt.phonerepair.modelclass.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SenOtpData {
    @SerializedName("user")
    @Expose
    private SendOtpUser user;
    @SerializedName("otp")
    @Expose
    private Integer otp;

    public SendOtpUser getUser() {
        return user;
    }

    public void setUser(SendOtpUser user) {
        this.user = user;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }


}
