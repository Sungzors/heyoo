package com.phdlabs.sungwon.heyoo.api.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.response.VerifyDataResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
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

    public static AccountManager getInstance(){
        return sInstance;
    }

    private AccountManager(){
        mEvents = EventsManager.getInstance().getDataEventBus();
        mCaller = Rest.getInstance().getHeyooEndpoint();
    }

    /*Must call this to init token if logged in*/
    public boolean isUserLogged(Context context){
        Preferences pref = new Preferences(context);
        if (mAccessToken == null) {
            mAccessToken = pref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null);
        }
        return mAccessToken != null;
    }

    public void login(@NonNull LoginData loginData){
        //TODO: move the login call from login to here, also do register
    }

    public void bindAccountData(VerifyDataResponse data, Context context) {
        mAccessToken = data.getToken();
        Preferences pref = new Preferences(context);
        pref.putPreference(Constants.PreferenceConstants.KEY_TOKEN, mAccessToken);
    }

}
