package com.phdlabs.sungwon.heyoo.model;

/**
 * Created by SungWon on 7/18/2017.
 */

public class HeyooAlert {

    private String id;
    private String title;
    private String body;
    private boolean unread;
    private String type;
    private String source;
    private HeyooAttendee sender;
    private HeyooCalendar calendar;
    private HeyooEvent event;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public HeyooAttendee getSender() {
        return sender;
    }

    public void setSender(HeyooAttendee sender) {
        this.sender = sender;
    }

    public HeyooCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(HeyooCalendar calendar) {
        this.calendar = calendar;
    }

    public HeyooEvent getEvent() {
        return event;
    }

    public void setEvent(HeyooEvent event) {
        this.event = event;
    }
}
