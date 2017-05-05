package com.phdlabs.sungwon.heyoo.structure.aahome;

import com.phdlabs.sungwon.heyoo.model.Event;

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
        List<Event> events = getTestEvents();
        view.showEvents(events);
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

    }

    @Override
    public void onDayUnselected() {

    }

    private List<Event> getTestEvents(){
        ArrayList<Event> eventList = new ArrayList<>();
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
        Event event1 = new Event(0, "Test Event 1", date1s, date1e, true, 0);
        Event event2 = new Event(0, "Test Event 2", date2s, date2e, true, 0);
        Event event3 = new Event(0, "Test Event 3", date3s, date3e, true, 1);
        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
        return eventList;
    }
}
