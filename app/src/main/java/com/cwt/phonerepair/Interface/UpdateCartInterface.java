package com.cwt.phonerepair.Interface;

import com.cwt.phonerepair.modelclass.response.cart.gettocart.GetToCartModel;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanModel;

public interface UpdateCartInterface {

    public  void  getUpdateCart(
            GetToCartModel getToCartModel,String action
    );

}
