package com.phdlabs.sungwon.heyoo.structure.abcalender.calendar;

import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;

/**
 * Created by SungWon on 6/12/2017.
 */

public class CalendarController implements CalendarContract.Controller {

    CalendarContract.View mView;

    CalendarController(CalendarContract.View mView){
        this.mView = mView;
    }

    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
}
