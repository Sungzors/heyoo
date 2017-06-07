package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 6/7/2017.
 */

public class RegisterResendCodeEvent extends Event {
    public RegisterResendCodeEvent() {
        super();
    }

    public RegisterResendCodeEvent(String errormessage){
        super(errormessage);
    }

}
