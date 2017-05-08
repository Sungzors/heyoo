package com.phdlabs.sungwon.heyoo.api.utility;

/**
 * Created by SungWon on 5/8/2017.
 */

public class AccessToken {
    private String token;
    private String type;
    private String expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "Bearer " + token;
    }
}
