package com.phdlabs.sungwon.heyoo.structure.aahome;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAlert;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends MainActivity<HomeContract.Activity.Controller>
        implements HomeContract.Activity.View{


    private HomeFragment mHomeFragment;

    private int imageEventID = -1;
    private HeyooEvent event;


    @NonNull
    @Override
    protected HomeContract.Activity.Controller createController() {
        return new HomeActivityController(this);
    }

    @Override
    protected int tabIndex() {
        return TAB_HOME;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, getHomeFragment()).commitAllowingStateLoss();
    }

    private Fragment getHomeFragment(){
        Fragment fragment;
        if (mHomeFragment == null){
            mHomeFragment = HomeFragment.newInstance();
        }
        fragment = mHomeFragment;
        return fragment;
    }

    public int getImageEventID(){
        int i = imageEventID;
        imageEventID = -1;
        return i;
    }

    public void setImageEventID(int ID){
        imageEventID = ID;
    }

    public void setEvent(HeyooEvent event){
        this.event = event;
    }

    public HeyooEvent getEvent(){
        return event;
    }

    public void setUpNotification(NotificationCompat.Builder mBuilder, HeyooAlert alert){
        Intent resultIntent = new Intent(this, HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.getInteger(alert.getId()), mBuilder.build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
