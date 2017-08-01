package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 8/1/2017.
 */

public class ChatTokenEvent extends Event {

    public ChatTokenEvent() {
    }

    public ChatTokenEvent(String errorMessage) {
        super(errorMessage);
    }
}
