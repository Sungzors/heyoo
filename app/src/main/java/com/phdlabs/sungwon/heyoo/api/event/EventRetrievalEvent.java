package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 6/21/2017.
 */

public class EventRetrievalEvent extends Event{
    public EventRetrievalEvent() {
        super();
    }

    public EventRetrievalEvent(String errorMessage) {
        super(errorMessage);
    }
}
