package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.User;

/**
 * Created by SungWon on 5/8/2017.
 */

public class UserDataResponse extends ErrorResponse {

    private User user;

    private HeyooCalendar calendar;

    public User getUser() {
        return user;
    }

    public HeyooCalendar getCalendar() {
        return calendar;
    }
}
