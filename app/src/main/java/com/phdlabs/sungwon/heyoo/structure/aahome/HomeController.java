package com.phdlabs.sungwon.heyoo.structure.aahome;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SungWon on 4/18/2017.
 */

public class HomeController implements HomeContract.Controller{

    private HomeContract.View view;

    HomeController(HomeContract.View view){
        this.view = view;
    }

    @Override
    public void onStart() {
        List<HeyooEvent> heyooEvents = getTestEvents();
        view.showEvents(heyooEvents);
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
        view.showEvents(getTestEvents());
        view.refreshList();
    }

    @Override
    public void onDayUnselected() {

    }

    private List<HeyooEvent> getTestEvents(){
        ArrayList<HeyooEvent> heyooEventList = new ArrayList<>();
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
        HeyooEvent heyooEvent1 = new HeyooEvent(0, "Test HeyooEvent 1", date1s, date1e, true, 0);
        HeyooEvent heyooEvent2 = new HeyooEvent(0, "Test HeyooEvent 2", date2s, date2e, true, 0);
        HeyooEvent heyooEvent3 = new HeyooEvent(0, "Test HeyooEvent 3", date3s, date3e, true, 1);
        heyooEventList.add(heyooEvent1);
        heyooEventList.add(heyooEvent2);
        heyooEventList.add(heyooEvent3);
        return heyooEventList;
    }
}
