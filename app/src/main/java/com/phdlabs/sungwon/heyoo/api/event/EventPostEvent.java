package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 6/21/2017.
 */

public class EventPostEvent extends Event{
    public EventPostEvent() {
        super();
    }

    public EventPostEvent(String errorMessage) {
        super(errorMessage);
    }
}
