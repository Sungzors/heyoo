package com.phdlabs.sungwon.heyoo.api.data;

import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;

import java.util.List;

/**
 * Created by SungWon on 7/11/2017.
 */

public class AttendeePostData {

    List<HeyooAttendee> attendees;

    public AttendeePostData(List<HeyooAttendee> attendees) {
        this.attendees = attendees;
    }
}
