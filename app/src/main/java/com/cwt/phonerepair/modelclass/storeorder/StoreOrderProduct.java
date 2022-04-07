package com.cwt.phonerepair.modelclass.storeorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreOrderProduct {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("product_imgae")
    @Expose
    private String productImgae;
    @SerializedName("discription")
    @Expose
    private String discription;
    @SerializedName("price")
    @Expose
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductImgae() {
        return productImgae;
    }

    public void setProductImgae(String productImgae) {
        this.productImgae = productImgae;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
