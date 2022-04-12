package com.cwt.phonerepair.modelclass.parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewParameter implements Serializable {


    public ReviewParameter(String userId, String storeId, String productId, String review, String rating) {
        this.userId = userId;
        this.storeId = storeId;
        this.productId = productId;
        this.review = review;
        this.rating = rating;
    }

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
