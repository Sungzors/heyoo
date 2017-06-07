package com.phdlabs.sungwon.heyoo.model;

/**
 * Created by SungWon on 5/8/2017.
 */

public class UserManager {
    private static UserManager mInstance;
    private User mUser;

    public static UserManager getInstance(){
        if (mInstance == null) {
            mInstance = new UserManager();
        }
        return mInstance;
    }

    private UserManager(){
        super();
    }

    public User getUser(){
        return mUser;
    }

    public void setUser(User user){
        this.mUser = user;
    }


}
