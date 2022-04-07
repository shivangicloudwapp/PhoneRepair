package com.cwt.phonerepair.modelclass.response.getStoreallProdcut;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStoreAllProductData {
    @SerializedName("product")
    @Expose
    private List<GetStoreAllProdcutModel> product = null;

    public List<GetStoreAllProdcutModel> getProduct() {
        return product;
    }

    public void setProduct(List<GetStoreAllProdcutModel> product) {
        this.product = product;
    }
}
