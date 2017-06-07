package com.phdlabs.sungwon.heyoo.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SungWon on 6/7/2017.
 */

public class ResendData {

    @SerializedName("phone")
    @Expose
    private String phone;

    public ResendData(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
