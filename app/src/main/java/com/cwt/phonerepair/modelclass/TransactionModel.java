package com.cwt.phonerepair.modelclass;

public class TransactionModel {

    String tvWith,tvMonth,tvTimeDate,tvRm;

    public TransactionModel(String tvWith, String tvMonth, String tvTimeDate,String tvRm) {
        this.tvWith = tvWith;
        this.tvMonth = tvMonth;
        this.tvTimeDate = tvTimeDate;
        this.tvRm = tvRm;
    }


    public String getTvWith() {
        return tvWith;
    }

    public String getTvMonth() {
        return tvMonth;
    }

    public String getTvTimeDate() {
        return tvTimeDate;
    }


    public void setTvWith(String tvWith) {
        this.tvWith = tvWith;
    }

    public void setTvMonth(String tvMonth) {
        this.tvMonth = tvMonth;
    }

    public void setTvTimeDate(String tvTimeDate) {
        this.tvTimeDate = tvTimeDate;
    }


    public String getTvRm() {
        return tvRm;
    }

    public void setTvRm(String tvRm) {
        this.tvRm = tvRm;
    }
}
