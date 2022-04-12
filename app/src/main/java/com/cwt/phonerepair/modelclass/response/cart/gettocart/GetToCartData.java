package com.cwt.phonerepair.modelclass.response.cart.gettocart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetToCartData {

    @SerializedName("cart")
    @Expose
    private List<GetToCartModel> cart = null;

    public List<GetToCartModel> getCart() {
        return cart;
    }

    public void setCart(List<GetToCartModel> cart) {
        this.cart = cart;
    }
}
