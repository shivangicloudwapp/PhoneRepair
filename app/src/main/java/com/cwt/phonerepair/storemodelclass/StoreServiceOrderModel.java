package com.cwt.phonerepair.storemodelclass;


public class StoreServiceOrderModel {

    String tvCode,tvDate,tvName;
    int userImg;


    public StoreServiceOrderModel(String tvCode, String tvDate, String tvName, int userImg) {
        this.tvCode = tvCode;
        this.tvDate = tvDate;
        this.tvName = tvName;
        this.userImg = userImg;
    }


    public String getTvCode() {
        return tvCode;
    }

    public String getTvDate() {
        return tvDate;
    }

    public String getTvName() {
        return tvName;
    }

    public int getUserImg() {
        return userImg;
    }


    public void setTvCode(String tvCode) {
        this.tvCode = tvCode;
    }

    public void setTvDate(String tvDate) {
        this.tvDate = tvDate;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public void setUserImg(int userImg) {
        this.userImg = userImg;
    }
}
