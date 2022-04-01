package com.cwt.phonerepair.modelclass.response;

import java.io.Serializable;

public class SignupData implements Serializable {
    private Integer userId;
    private Integer otp;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
