package com.cwt.phonerepair.modelclass;

public class OurExclusiveStoreModel {
    String tvStoreName,tvAddress;
    int ivStoreimg;

    public OurExclusiveStoreModel(String tvStoreName, String tvAddress, int ivStoreimg) {
        this.tvStoreName = tvStoreName;
        this.tvAddress = tvAddress;
        this.ivStoreimg = ivStoreimg;
    }

    public String getTvStoreName() {
        return tvStoreName;
    }

    public String getTvAddress() {
        return tvAddress;
    }

    public void setTvStoreName(String tvStoreName) {
        this.tvStoreName = tvStoreName;
    }

    public void setTvAddress(String tvAddress) {
        this.tvAddress = tvAddress;
    }

    public int getIvStoreimg() {
        return ivStoreimg;
    }

    public void setIvStoreimg(int ivStoreimg) {
        this.ivStoreimg = ivStoreimg;
    }
}
