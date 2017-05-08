package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.User;

/**
 * Created by SungWon on 5/8/2017.
 */

public class UserDataResponse extends ErrorResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public User getUser() {
        return data.getUser();
    }

    public static class Data {

        private User user;

        public User getUser() {
            return user;
        }

    }
}
