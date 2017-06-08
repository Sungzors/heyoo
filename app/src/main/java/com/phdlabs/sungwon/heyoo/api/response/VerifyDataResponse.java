package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.User;

/**
 * Created by SungWon on 6/7/2017.
 */

public class VerifyDataResponse extends ErrorResponse {


    private String token;

    private User user;


    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

}
