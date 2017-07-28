package com.phdlabs.sungwon.heyoo.structure.launch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.event.CalendarRetrievalEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventRetrievalEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooAlertManager;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendarManager;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;
import com.phdlabs.sungwon.heyoo.model.UserManager;
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
        int userID = mPref.getPreferenceInt(Constants.PreferenceConstants.USER_ID, 0);
        if(TextUtils.isEmpty(mToken)|| userID==0){
            showLogin();
        } else {
            UserManager userManager = UserManager.getInstance(mToken, userID);
            userManager.loadUsers();
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

    public void showError(String errorMessage, final boolean isCalendar) {
        new AlertDialog.Builder(this).setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(isCalendar){
                            mCalendarManager.loadCalendars();
                        } else {
                            mEventManager.loadEvents();
                        }
                    }
                }).show();
    }

    @Subscribe
    public void onEventMainThread(CalendarRetrievalEvent event){
        if (event.isSuccess()){
            mCalendarManager.getEventBus().unregister(this);
            mEventManager.getEventBus().register(this);
            mEventManager.loadEvents();
        } else {
            showError(event.getErrorMessage(), true);
        }
    }

    @Subscribe
    public void onEventMainThread(EventRetrievalEvent event){
        if (event.isSuccess()){
            hideProgress();
            mEventManager.getEventBus().unregister(this);
            openApp();
        } else {
            showError(event.getErrorMessage(), false);
        }
    }
}
