package com.phdlabs.sungwon.heyoo.api.data;

import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.utility.AccessToken;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by SungWon on 5/9/2017.
 */

public class AccountManager {

    private static AccountManager sInstance = new AccountManager();

    private AccessToken mAccessToken;
    private HeyooEndpoint mCaller;
    private EventBus mEvents;

    public static AccountManager getInstance(){
        return sInstance;
    }

}
