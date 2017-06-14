package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;

import java.util.List;

/**
 * Created by SungWon on 6/13/2017.
 */

public class CalendarRetrievalResponse extends ErrorResponse {

    List<HeyooCalendar> calendar;

    public List<HeyooCalendar> getCalendar() {
        return calendar;
    }
}
