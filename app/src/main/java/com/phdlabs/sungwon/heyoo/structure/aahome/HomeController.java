package com.phdlabs.sungwon.heyoo.structure.aahome;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;

import java.util.List;

/**
 * Created by SungWon on 4/18/2017.
 */

public class HomeController implements HomeContract.Controller{

    private HomeContract.View mView;

    private HeyooEventManager mEventManager;

    HomeController(HomeContract.View mView){
        this.mView = mView;
    }

    @Override
    public void onStart() {
        mEventManager = HeyooEventManager.getInstance();
        List<HeyooEvent> heyooEvents = mEventManager.getEvents(mView.getCalendarId());
        mView.showEvents(heyooEvents);
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

    @Override
    public void onDaySelected() {
        mView.showEvents(mEventManager.getEvents());
        mView.refreshList();
    }

    @Override
    public void onDayUnselected() {

    }


}
