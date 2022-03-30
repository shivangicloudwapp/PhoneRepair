package com.cwt.phonerepair.modelclass;

public class AcceptedModel {
    String tvCode,tvTimeDate;

    public AcceptedModel( String tvCode, String tvTimeDate) {
        this.tvCode = tvCode;
        this.tvTimeDate = tvTimeDate;
    }

    public String getTvCode() {
        return tvCode;
    }

    public String getTvTimeDate() {
        return tvTimeDate;
    }

    public void setTvCode(String tvCode) {
        this.tvCode = tvCode;
    }

    public void setTvTimeDate(String tvTimeDate) {
        this.tvTimeDate = tvTimeDate;
    }
}
