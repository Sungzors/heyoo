package com.phdlabs.sungwon.heyoo.utility;

import com.google.gson.Gson;

/**
 * Created by SungWon on 4/25/2017.
 */

public class GsonHolder {
    private static GsonHolder sInstance;

    private Gson gson;
    public static GsonHolder getInstance() {
        if (sInstance == null) {
            sInstance = new GsonHolder();
        }
        return sInstance;
    }

    private GsonHolder() {
        gson = new Gson();
    }

    public static Gson get() {
        return getInstance().gson;
    }
}
