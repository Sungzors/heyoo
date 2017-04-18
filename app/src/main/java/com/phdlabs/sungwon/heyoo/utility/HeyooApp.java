package com.phdlabs.sungwon.heyoo.utility;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by SungWon on 4/18/2017.
 */

public class HeyooApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
