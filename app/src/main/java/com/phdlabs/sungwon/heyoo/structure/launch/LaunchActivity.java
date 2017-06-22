package com.phdlabs.sungwon.heyoo.structure.launch;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.event.CalendarRetrievalEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventRetrievalEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendarManager;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;
import com.phdlabs.sungwon.heyoo.structure.aahome.HomeActivity;
import com.phdlabs.sungwon.heyoo.structure.core.BaseActivity;
import com.phdlabs.sungwon.heyoo.structure.login.login.LoginFragment;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by SungWon on 6/22/2017.
 */

public class LaunchActivity extends BaseActivity {

    private HeyooCalendarManager mCalendarManager;
    private HeyooEventManager mEventManager;
    private Preferences mPref;
    private String mToken;

    @Override
    protected int layoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected int contentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = new Preferences(this);
        mToken = mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null);
        if(TextUtils.isEmpty(mToken)){
            showLogin();
        } else {
            mCalendarManager = HeyooCalendarManager.getInstance(mToken);
            mEventManager = HeyooEventManager.getInstance(mToken);
            mCalendarManager.loadCalendars();
        }
    }

    private void showLogin() {
        replaceFragment(new LoginFragment(), false);
    }

    private void openApp() {
        Intent intent = new Intent(LaunchActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventsManager.getInstance().getDataEventBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventsManager.getInstance().getDataEventBus().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(CalendarRetrievalEvent event){
        mEventManager.loadEvents();
    }


    @Subscribe
    public void onEventMainThread(EventRetrievalEvent event){
        openApp();
    }
}
