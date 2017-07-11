package com.phdlabs.sungwon.heyoo.model;

import java.io.Serializable;

/**
 * Created by SungWon on 7/11/2017.
 */

public class HeyooAttendeeStatusNest implements Serializable{
    private String status;
    private int event_id;
    private int user_id;

    public HeyooAttendeeStatusNest(String status, int event_id, int user_id) {
        this.status = status;
        this.event_id = event_id;
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public int getEvent_id() {
        return event_id;
    }

    public int getUser_id() {
        return user_id;
    }
}
