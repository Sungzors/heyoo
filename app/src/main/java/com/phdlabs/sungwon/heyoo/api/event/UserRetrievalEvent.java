package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 7/10/2017.
 */

public class UserRetrievalEvent extends Event {

    public UserRetrievalEvent() {
    }

    public UserRetrievalEvent(String errorMessage) {
        super(errorMessage);
    }
}
