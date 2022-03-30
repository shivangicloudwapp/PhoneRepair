package com.cwt.phonerepair.modelclass;

public class PendingModel {
    String tvCode,tvTimeDate;

    public PendingModel( String tvCode, String tvTimeDate) {
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
