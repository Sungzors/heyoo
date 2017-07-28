package com.phdlabs.sungwon.heyoo.api.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.response.VerifyDataResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.model.UserManager;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by SungWon on 5/9/2017.
 */

public class AccountManager {

    private static AccountManager sInstance = new AccountManager();

    private String mAccessToken;
    private HeyooEndpoint mCaller;
    private EventBus mEvents;
    private Preferences mPref;

    public static AccountManager getInstance(){
        return sInstance;
    }

    private AccountManager(){
        mEvents = EventsManager.getInstance().getDataEventBus();
        mCaller = Rest.getInstance().getHeyooEndpoint();
    }

    /*Must call this to init token if logged in*/
    public boolean isUserLogged(Context context){
        mPref = new Preferences(context);
        if (mAccessToken == null) {
            mAccessToken = mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null);
        }
        return mAccessToken != null;
    }

    public void login(@NonNull LoginData loginData){
        //TODO: move the login call from login to here, also do register
    }

    public String debugGetKey(Context context){
        mPref = new Preferences(context);
        return mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null);
    }//TODO:debug, get rid of

    public String getKey(){
        return mAccessToken;
    }

    public void bindAccountData(VerifyDataResponse data, Context context) {
        mAccessToken = data.getToken();
        mPref = new Preferences(context);
        mPref.putPreference(Constants.PreferenceConstants.KEY_TOKEN, mAccessToken);
        mPref.putPreference(Constants.PreferenceConstants.USER_ID, data.getUser().getId());
        UserManager userManager = UserManager.getInstance();
        userManager.setUser(data.getUser());
    }

}
