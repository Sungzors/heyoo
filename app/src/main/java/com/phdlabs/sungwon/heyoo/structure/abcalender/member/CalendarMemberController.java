package com.phdlabs.sungwon.heyoo.structure.abcalender.member;

import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;

/**
 * Created by SungWon on 7/13/2017.
 */

public class CalendarMemberController implements CalendarContract.Member.Controller{

    CalendarContract.Member.View mView;

    CalendarMemberController(CalendarContract.Member.View mView){
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
