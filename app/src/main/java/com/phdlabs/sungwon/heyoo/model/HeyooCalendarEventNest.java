package com.phdlabs.sungwon.heyoo.model;

/**
 * Created by SungWon on 6/22/2017.
 */

public class HeyooCalendarEventNest {

    private int id;
    private HeyooCalendarDetailNest calendars_events;

    public HeyooCalendarEventNest(int id, HeyooCalendarDetailNest calendars_events) {
        this.id = id;
        this.calendars_events = calendars_events;
    }

    public int getId() {
        return id;
    }

    public HeyooCalendarDetailNest getCalendars_events() {
        return calendars_events;
    }
}
