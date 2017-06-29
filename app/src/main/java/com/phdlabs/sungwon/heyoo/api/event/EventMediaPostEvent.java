package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 6/28/2017.
 */

public class EventMediaPostEvent extends Event {
    public EventMediaPostEvent() {
        super();
    }

    public EventMediaPostEvent(String errorMessage) {
        super(errorMessage);
    }
}
