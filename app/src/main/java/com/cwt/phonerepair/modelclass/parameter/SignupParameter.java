package com.cwt.phonerepair.modelclass.parameter;

import java.io.Serializable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupParameter {
    public SignupParameter(String name, String mailtoEmail, String password, String deviceId, String fcmToken) {
        this.name = name;
        this.mailtoEmail = mailtoEmail;
        this.password = password;
        this.deviceId = deviceId;
        this.fcmToken = fcmToken;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String mailtoEmail;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailtoEmail() {
        return mailtoEmail;
    }

    public void setMailtoEmail(String mailtoEmail) {
        this.mailtoEmail = mailtoEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

}