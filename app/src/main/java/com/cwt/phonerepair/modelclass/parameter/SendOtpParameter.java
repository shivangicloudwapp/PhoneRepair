package com.cwt.phonerepair.modelclass.parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpParameter {


    public SendOtpParameter(Integer userId, String countryCode, String number) {
        this.userId = userId;
        this.countryCode = countryCode;
        this.number = number;
    }

    @SerializedName("User_id")
    @Expose
    private Integer userId;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("Number")
    @Expose
    private String number;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}