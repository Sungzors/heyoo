package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 7/27/2017.
 */

public class UserPatchEvent extends Event {

    public UserPatchEvent() {
    }

    public UserPatchEvent(String errorMessage) {
        super(errorMessage);
    }
}
