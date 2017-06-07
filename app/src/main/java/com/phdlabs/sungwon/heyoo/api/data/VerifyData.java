package com.phdlabs.sungwon.heyoo.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SungWon on 5/9/2017.
 */

public class VerifyData {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("passcode")
    @Expose
    private String passcode;

    public VerifyData(String phone, String passcode){
        this.phone = phone;
        this.passcode = passcode;
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

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
