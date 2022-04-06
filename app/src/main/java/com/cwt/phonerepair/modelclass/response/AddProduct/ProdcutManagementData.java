package com.cwt.phonerepair.modelclass.response.AddProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProdcutManagementData {

    @SerializedName("product")
    @Expose
    private List<ProductManagementModel> product = null;

    public List<ProductManagementModel> getProduct() {
        return product;
    }

    public void setProduct(List<ProductManagementModel> product) {
        this.product = product;
    }
}
