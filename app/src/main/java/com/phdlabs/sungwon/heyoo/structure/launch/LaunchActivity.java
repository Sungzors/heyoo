package com.phdlabs.sungwon.heyoo.structure.launch;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.event.CalendarRetrievalEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventRetrievalEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooAlertManager;
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
    private HeyooAlertManager mAlertManager;
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
        mPref = new Preferences(this);
        mToken = mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null);
        if(TextUtils.isEmpty(mToken)){
            showLogin();
        } else {
            mCalendarManager = HeyooCalendarManager.getInstance(mToken);
            mEventManager = HeyooEventManager.getInstance(mToken);
            mAlertManager = HeyooAlertManager.getInstance(mToken);
            mCalendarManager.getEventBus().register(this);
            mCalendarManager.loadCalendars();
            mAlertManager.loadAlerts();
            showProgress();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Subscribe
    public void onEventMainThread(CalendarRetrievalEvent event){
        if (event.isSuccess()){
            mCalendarManager.getEventBus().unregister(this);
            mEventManager.getEventBus().register(this);
            mEventManager.loadEvents();
        } else {
            showError(event.getErrorMessage());
        }
    }


    @Subscribe
    public void onEventMainThread(EventRetrievalEvent event){
        if (event.isSuccess()){
            hideProgress();
            mEventManager.getEventBus().unregister(this);
            openApp();
        } else {
            showError(event.getErrorMessage());
        }
    }
}
