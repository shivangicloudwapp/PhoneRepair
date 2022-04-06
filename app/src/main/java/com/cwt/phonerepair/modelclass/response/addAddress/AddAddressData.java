package com.cwt.phonerepair.modelclass.response.addAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAddressData {
    @SerializedName("address")
    @Expose
    private AddAddressModel address;

    public AddAddressModel getAddress() {
        return address;
    }

    public void setAddress(AddAddressModel address) {
        this.address = address;
    }
}
