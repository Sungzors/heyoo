package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 7/27/2017.
 */

public class UserGetEvent extends Event {

    public UserGetEvent() {
    }

    public UserGetEvent(String errorMessage) {
        super(errorMessage);
    }
}
