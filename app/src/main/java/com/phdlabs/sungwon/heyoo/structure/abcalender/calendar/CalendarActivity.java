package com.phdlabs.sungwon.heyoo.structure.abcalender.calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by SungWon on 6/12/2017.
 */

public class CalendarActivity extends MainActivity<CalendarContract.Activity.Controller>
        implements CalendarContract.Activity.View{

    private CalendarFragment mCalendarFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, getCalendarFragment()).commitAllowingStateLoss();
    }

    private Fragment getCalendarFragment(){
        Fragment fragment;
        if (mCalendarFragment == null){
            mCalendarFragment = CalendarFragment.newInstance();
        }
        fragment = mCalendarFragment;
        return fragment;
    }
    @NonNull
    @Override
    protected CalendarContract.Activity.Controller createController() {
        return new CalendarActivityController(this);
    }

    @Override
    protected int tabIndex() {
        return TAB_CALENDAR;
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
