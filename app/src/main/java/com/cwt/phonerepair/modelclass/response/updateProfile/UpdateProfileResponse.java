package com.cwt.phonerepair.modelclass.response.updateProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {





    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("massage")
    @Expose
    private String massage;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

}
