package com.cwt.phonerepair.modelclass;

public class OrderModel {

    String tvItemNum,tvCode,tvTimeDate;

    public OrderModel(String tvItemNum, String tvCode, String tvTimeDate) {
        this.tvItemNum = tvItemNum;
        this.tvCode = tvCode;
        this.tvTimeDate = tvTimeDate;
    }

    public String getTvItemNum() {
        return tvItemNum;
    }

    public String getTvCode() {
        return tvCode;
    }

    public String getTvTimeDate() {
        return tvTimeDate;
    }


    public void setTvItemNum(String tvItemNum) {
        this.tvItemNum = tvItemNum;
    }

    public void setTvCode(String tvCode) {
        this.tvCode = tvCode;
    }

    public void setTvTimeDate(String tvTimeDate) {
        this.tvTimeDate = tvTimeDate;
    }
}
