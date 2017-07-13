package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 7/13/2017.
 */

public class CalendarUserPostEvent extends Event {

    public CalendarUserPostEvent() {
    }

    public CalendarUserPostEvent(String errorMessage) {
        super(errorMessage);
    }
}
