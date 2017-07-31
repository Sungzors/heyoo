package com.phdlabs.sungwon.heyoo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SungWon on 4/25/2017.
 */

public class HeyooEvent implements Serializable{

    private int id;
    private String name;
    private Date start_time;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private Date end_time;
    private boolean allDay;
    private List<HeyooCalendarEventNest> calendars;
    private String privacy;
    private String description;
    private String address;
    private int recurrence_id;
    private boolean published = true;
    private List<HeyooAttendee> attendees;
    private List<HeyooMedia> media;
    private List<HeyooAttachment> attachments;

    public static int hashCode(Calendar calendar) {
            //Should produce hashes like "20170401"
        return (calendar.get(Calendar.YEAR) * 10000) + ((calendar.get(Calendar.MONTH)+1) * 100) + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public HeyooEvent(int id, String name, Date start_time, Date end_time, String description, boolean allDay, int calendars, String address) {
        this.id = id;
        this.name = name;
        this.start_time = start_time;
        startCalendar = Calendar.getInstance();
        startCalendar.setTime(start_time);
        this.end_time = end_time;
        endCalendar = Calendar.getInstance();
        endCalendar.setTime(end_time);
        this.description = description;
        this.allDay = allDay;
        this.calendars = new ArrayList<>();
        this.calendars.add(new HeyooCalendarEventNest(calendars, null));
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getStartCalendar() {
        return startCalendar;
    }

    public Calendar getEndCalendar() {
        return endCalendar;
    }

    public int getStartTimeHash() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(start_time);
        return hashCode(cal);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public int getCalendars() {
        if(calendars.isEmpty()){
            return -1;
        }
        return calendars.get(0).getId();
    }

    public void setCalendars(int calendars) {
        this.calendars.set(0,new HeyooCalendarEventNest(calendars, null));
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<HeyooAttendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<HeyooAttendee> attendees) {
        this.attendees = attendees;
    }

    public List<HeyooMedia> getMedia() {
        return media;
    }

    public void addMedia(String url){
        media.add(new HeyooMedia(null, null, url, id));
    }

    public void setMedia(List<HeyooMedia> media) {
        this.media = media;
    }

    public List<HeyooAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<HeyooAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public int getRecurrence_id() {
        return recurrence_id;
    }

    public void setRecurrence_id(int recurrence_id) {
        this.recurrence_id = recurrence_id;
    }
}