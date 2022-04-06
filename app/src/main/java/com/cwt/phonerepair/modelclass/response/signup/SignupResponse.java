package com.cwt.phonerepair.modelclass.response.signup;

import java.io.Serializable;

public class SignupResponse implements Serializable {
    private Boolean status;
    private SignupData data;
    private String massage;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public SignupData getData() {
        return data;
    }

    public void setData(SignupData data) {
        this.data = data;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
