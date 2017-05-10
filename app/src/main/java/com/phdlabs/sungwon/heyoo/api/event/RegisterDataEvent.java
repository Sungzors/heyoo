package com.phdlabs.sungwon.heyoo.api.event;


/**
 * Created by SungWon on 5/9/2017.
 */

public class RegisterDataEvent extends Event {
    public RegisterDataEvent() {
        super();
    }

    public RegisterDataEvent(String errormessage){
        super(errormessage);
    }
}
