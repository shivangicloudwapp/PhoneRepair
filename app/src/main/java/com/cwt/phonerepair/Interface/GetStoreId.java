package com.cwt.phonerepair.Interface;

import com.cwt.phonerepair.modelclass.response.allStores.AllStoreModel;
import com.cwt.phonerepair.modelclass.response.storedetails.StoreDetailsProductModel;
import com.cwt.phonerepair.modelclass.response.subscriptionPlan.SubscriptionPlanModel;

public interface GetStoreId {

    public  void  getStoreId(
            AllStoreModel allStoreModel
    );
}
