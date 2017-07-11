package com.phdlabs.sungwon.heyoo.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by SungWon on 6/22/2017.
 */

public class HeyooCalendarDetailNest implements Serializable{

    private Date created_at;
    private Date updated_at;
    private int calendar_id;
    private int event_id;

    public HeyooCalendarDetailNest(Date created_at, Date updated_at, int calendar_id, int event_id) {
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.calendar_id = calendar_id;
        this.event_id = event_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public int getCalendar_id() {
        return calendar_id;
    }

    public int getEvent_id() {
        return event_id;
    }
}
