package com.phdlabs.sungwon.heyoo.api.data;

import com.phdlabs.sungwon.heyoo.model.HeyooAttachment;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;

import java.util.Date;
import java.util.List;

/**
 * Created by SungWon on 6/21/2017.
 */

public class EventPostData {

    private String name;
    private Date start_time;
    private Date end_time;
    private String privacy;
    private boolean published;
    private String description;
    private List<HeyooAttendee> attendees;
    private int calendar_id;
    private List<HeyooMedia> media;
    private List<HeyooAttachment> attachments;
    private RecurrenceData recurrence;

    public EventPostData(HeyooEvent event, RecurrenceData recurrence) {
        name = event.getName();
        start_time = event.getStart_time();
        end_time = event.getEnd_time();
        privacy = event.getPrivacy();
        published = event.isPublished();
        description = event.getDescription();
        attendees = event.getAttendees();
        calendar_id = event.getCalendars();
        media = event.getMedia();
        attachments = event.getAttachments();
        this.recurrence = recurrence;
    }
}
