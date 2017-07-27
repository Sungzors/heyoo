package com.phdlabs.sungwon.heyoo.api.data;

/**
 * Created by SungWon on 7/27/2017.
 */

public class UserPatchData {

    private String first_name;
    private String last_name;
    private String email;
    private String device_id;
    private String facebook_id;
    private String phone;
    private String country_code;

    public UserPatchData(String first_name, String last_name, String email, String device_id, String facebook_id, String phone, String country_code) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.device_id = device_id;
        this.facebook_id = facebook_id;
        this.phone = phone;
        this.country_code = country_code;
    }
}
