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
    @SerializedName("country_code")
    @Expose
    private String country_code;

    public ResendData(String phone, String country_code) {
        this.phone = phone;
        this.country_code = country_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
