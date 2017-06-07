package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 6/7/2017.
 */

public class VerifyDataEvent extends Event {

    public VerifyDataEvent() {
        super();
    }

    public VerifyDataEvent(String errormessage){
        super(errormessage);
    }
}
