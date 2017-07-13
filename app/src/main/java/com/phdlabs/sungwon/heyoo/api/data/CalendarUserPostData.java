package com.phdlabs.sungwon.heyoo.api.data;

import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;

import java.util.List;

/**
 * Created by SungWon on 7/13/2017.
 */

public class CalendarUserPostData {

    List<HeyooAttendee> users;

    public CalendarUserPostData(List<HeyooAttendee> users) {
        this.users = users;
    }
}
