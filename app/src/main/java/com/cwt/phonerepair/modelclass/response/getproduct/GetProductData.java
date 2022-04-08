package com.cwt.phonerepair.modelclass.response.getproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductData {

    @SerializedName("product")
    @Expose
    private GetProductModel product;

    public GetProductModel getProduct() {
        return product;
    }

    public void setProduct(GetProductModel product) {
        this.product = product;
    }
}
