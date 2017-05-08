package com.phdlabs.sungwon.heyoo.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SungWon on 5/8/2017.
 */

public class LoginData {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("country_code")
    @Expose
    private String country_code;
    @SerializedName("email")
    @Expose
    private String email;

    public LoginData(String phone, String country_code, String email){
        this.phone = phone;
        this.country_code = country_code;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * @param phone must be a 10 digit phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry_code() {
        return country_code;
    }

    /**
     * Must include +, i.e. "+1"
     */
    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
