package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;

import java.util.List;

/**
 * Created by SungWon on 6/21/2017.
 */

public class EventRetrievalResponse extends ErrorResponse{

    List<HeyooEvent> calEvents;

    public List<HeyooEvent> getCalEvents() {
        return calEvents;
    }
}
