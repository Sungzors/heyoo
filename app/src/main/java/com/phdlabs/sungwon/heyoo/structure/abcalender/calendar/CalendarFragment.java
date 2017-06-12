package com.phdlabs.sungwon.heyoo.structure.abcalender.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 6/12/2017.
 */

public class CalendarFragment extends BaseFragment<CalendarContract.Controller>
        implements CalendarContract.View{

    private static final String TAG = "CalendarFragment";

    @NonNull
    @Override
    protected CalendarContract.Controller createController() {
        return new CalendarController(this);
    }

    public static CalendarFragment newInstance(){
        Bundle args = new Bundle();
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_calendar;
    }
}
