package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;

/**
 * Created by SungWon on 6/21/2017.
 */

public class EventPostResponse extends ErrorResponse{

    private HeyooEvent calEvent;

    public HeyooEvent getCalEvent() {
        return calEvent;
    }
}
