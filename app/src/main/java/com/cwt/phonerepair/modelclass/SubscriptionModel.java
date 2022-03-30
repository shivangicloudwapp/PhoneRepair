package com.cwt.phonerepair.modelclass;

public class SubscriptionModel {

    String tvPlanName,tvMonth,tvPostItem;


    public SubscriptionModel(String tvPlanName, String tvMonth, String tvPostItem) {
        this.tvPlanName = tvPlanName;
        this.tvMonth = tvMonth;
        this.tvPostItem = tvPostItem;
    }

    public String getTvPlanName() {
        return tvPlanName;
    }

    public void setTvPlanName(String tvPlanName) {
        this.tvPlanName = tvPlanName;
    }

    public String getTvMonth() {
        return tvMonth;
    }

    public void setTvMonth(String tvMonth) {
        this.tvMonth = tvMonth;
    }

    public String getTvPostItem() {
        return tvPostItem;
    }

    public void setTvPostItem(String tvPostItem) {
        this.tvPostItem = tvPostItem;
    }
}
