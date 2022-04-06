package com.cwt.phonerepair.modelclass.response.getAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAddressData {
    @SerializedName("address")
    @Expose
    private List<GetAddressModel> address = null;

    public List<GetAddressModel> getAddress() {
        return address;
    }

    public void setAddress(List<GetAddressModel> address) {
        this.address = address;
    }
}
