package com.cwt.phonerepair.modelclass;

public class ProductModel {

    String tvProductName,tvProductId;
    int ivProdcut;

    public ProductModel(String tvProductName, String tvProductId,int ivProdcut) {
        this.tvProductName = tvProductName;
        this.tvProductId = tvProductId;
        this.ivProdcut = ivProdcut;
    }

    public String getTvProductName() {
        return tvProductName;
    }

    public String getTvProductId() {
        return tvProductId;
    }


    public void setTvProductName(String tvProductName) {
        this.tvProductName = tvProductName;
    }

    public void setTvProductId(String tvProductId) {
        this.tvProductId = tvProductId;
    }

    public int getIvProdcut() {
        return ivProdcut;
    }

    public void setIvProdcut(int ivProdcut) {
        this.ivProdcut = ivProdcut;
    }
}
