package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.User;

/**
 * Created by SungWon on 6/7/2017.
 */

public class VerifyDataResponse extends ErrorResponse {

    private Data data;

    private String token;

    private User user;

    public Data getData() {
        return data;
    }

    public User getUser() {
        return data.getUser();
    }

    public String getToken() {
        return data.getToken();
    }

    public static class Data {
        private User user;
        private String token;

        public User getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }
    }
}
