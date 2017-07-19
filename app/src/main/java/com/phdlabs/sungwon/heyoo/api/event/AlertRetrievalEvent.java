package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 7/18/2017.
 */

public class AlertRetrievalEvent extends Event {

    public AlertRetrievalEvent() {
    }

    public AlertRetrievalEvent(String errorMessage) {
        super(errorMessage);
    }
}
