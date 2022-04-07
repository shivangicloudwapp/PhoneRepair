package com.cwt.phonerepair.modelclass.parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddtoCartParameter {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("qty")
    @Expose
    private String qty;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
