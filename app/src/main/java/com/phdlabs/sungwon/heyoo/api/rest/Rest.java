package com.phdlabs.sungwon.heyoo.api.rest;

import com.phdlabs.sungwon.heyoo.api.utility.HttpManager;

/**
 * Created by SungWon on 5/8/2017.
 */

public class Rest {
    private static Rest ourInstance = new Rest();
    private HeyooEndpoint mEndpoint;

    public static Rest getInstance() {
        return ourInstance;
    }

    private Rest() {

    }

    public HeyooEndpoint getHeyooEndpoint() {
        if (mEndpoint == null) {
            mEndpoint = HttpManager.getInstance().getRetrofit().create(HeyooEndpoint.class);
        }
        return mEndpoint;
    }
}
