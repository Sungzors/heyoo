package com.phdlabs.sungwon.heyoo.api.data;

import java.util.Date;

/**
 * Created by SungWon on 6/21/2017.
 */

public class EventPatchData {

    private String name;
    private Date start_time;
    private Date end_time;
    private String privacy;
    private boolean published;
    private String description;

    public EventPatchData(String name, Date start_time, Date end_time, String privacy, boolean published, String description) {
        this.name = name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.privacy = privacy;
        this.published = published;
        this.description = description;
    }
}
