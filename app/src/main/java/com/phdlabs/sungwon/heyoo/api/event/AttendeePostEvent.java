package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 7/9/2017.
 */

public class AttendeePostEvent extends Event{

    public AttendeePostEvent() {
    }

    public AttendeePostEvent(String errorMessage) {
        super(errorMessage);
    }
}
