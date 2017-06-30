package com.phdlabs.sungwon.heyoo.structure.aahome;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;

import java.util.Calendar;
import java.util.Date;
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
        List<HeyooEvent> heyooEvents = mEventManager.getEvents();
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

    private void getTestEvents(){
        Calendar cal1s = Calendar.getInstance();
        Date date1s = cal1s.getTime();
        Calendar cal2s = Calendar.getInstance();
        Date date2s = cal2s.getTime();
        Calendar cal3s = Calendar.getInstance();
        cal3s.add(Calendar.DATE, 1);
        Date date3s = cal3s.getTime();
        Calendar cal1e = Calendar.getInstance();
        cal1e.add(Calendar.DATE, 2);
        Date date1e = cal1e.getTime();
        Calendar cal2e = Calendar.getInstance();
        cal2e.add(Calendar.DATE, 3);
        Date date2e = cal2e.getTime();
        Calendar cal3e = Calendar.getInstance();
        cal3e.add(Calendar.DATE, 3);
        Date date3e = cal3e.getTime();
        HeyooEvent heyooEvent1 = new HeyooEvent(0, "Test HeyooEvent 1", date1s, date1e, "Testing HeyooEvent1 Description", true, 0, "10 Address Lane\nGorp, CA 90000");
        HeyooEvent heyooEvent2 = new HeyooEvent(1, "Test HeyooEvent 2", date2s, date2e, "Testing HeyooEvent2 Description", true, 0, "11 Address Lane\nGorp, CA 90001");
        HeyooEvent heyooEvent3 = new HeyooEvent(2, "Test HeyooEvent 3", date3s, date3e, "Testing HeyooEvent3 Description", true, 1, "12 Address Lane\nGorp, CA 90002");
        mEventManager.addEvents(heyooEvent1);
        mEventManager.addEvents(heyooEvent2);
        mEventManager.addEvents(heyooEvent3);
    }
}
