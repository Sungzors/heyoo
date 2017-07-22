package com.phdlabs.sungwon.heyoo.utility;

import android.app.Application;

import com.phdlabs.sungwon.heyoo.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by SungWon on 4/18/2017.
 */

public class HeyooApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NunitoSans-Black.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
