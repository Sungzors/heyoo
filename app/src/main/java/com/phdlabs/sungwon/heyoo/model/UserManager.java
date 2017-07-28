package com.phdlabs.sungwon.heyoo.model;

import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.event.UserGetEvent;
import com.phdlabs.sungwon.heyoo.api.response.UserPatchResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

/**
 * Created by SungWon on 5/8/2017.
 */

public class UserManager {
    private static UserManager mInstance;
    private User mUser;
    private String mToken;
    private HeyooEndpoint mCaller;
    private EventBus mEvents;
    private int mUserID;

    public static UserManager getInstance(String token, int userID){
        if (mInstance == null) {
            mInstance = new UserManager(token, userID);
        }
        return mInstance;
    }

    public static UserManager getInstance(){
        if (mInstance == null) {
            mInstance = new UserManager();
        }
        return mInstance;
    }

    private UserManager(String token, int userID){
        super();
        mToken = token;
        mCaller = Rest.getInstance().getHeyooEndpoint();
        mEvents = EventsManager.getInstance().getDataEventBus();
        mUserID = userID;
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

    public void loadUsers(){
        Call<UserPatchResponse> call = mCaller.getProfile(mUserID, mToken);
        call.enqueue(new HCallback<UserPatchResponse, UserGetEvent>(mEvents) {
            @Override
            protected void onSuccess(UserPatchResponse data) {
                mUser = data.getUser();
                mEvents.post(new UserGetEvent());
            }
        });
    }


}
