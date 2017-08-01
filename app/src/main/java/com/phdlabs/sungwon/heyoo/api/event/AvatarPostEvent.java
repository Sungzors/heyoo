package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 7/31/2017.
 */

public class AvatarPostEvent extends Event {

    public AvatarPostEvent() {
    }

    public AvatarPostEvent(String errorMessage) {
        super(errorMessage);
    }
}
