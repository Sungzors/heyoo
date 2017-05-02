package com.phdlabs.sungwon.heyoo.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by SungWon on 4/25/2017.
 */

public class Event {

    private int id;
    private String name;
    private Date start_time;
    private Calendar startCalendar;
    private transient int startTimeHash;
    private Calendar endCalendar;
    private Date end_time;
    private boolean allDay;
    private int calendar_id;

    public static int hashCode(Calendar calendar) {
            //Should produce hashes like "20170401"
        return (calendar.get(Calendar.YEAR) * 10000) + ((calendar.get(Calendar.MONTH)+1) * 100) + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public Event(int id, String name, Date start_time, Date end_time, boolean allDay, int calendar_id) {
        this.id = id;
        this.name = name;
        this.start_time = start_time;
        startCalendar = Calendar.getInstance();
        startCalendar.setTime(start_time);
        startTimeHash = hashCode(startCalendar);
        this.end_time = end_time;
        endCalendar = Calendar.getInstance();
        endCalendar.setTime(end_time);
        this.allDay = allDay;
        this.calendar_id = calendar_id;
    }



    public Calendar getStartCalendar() {
        return startCalendar;
    }

    public Calendar getEndCalendar() {
        return endCalendar;
    }

    public int getStartTimeHash() {
        return startTimeHash;
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

    public int getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(int calendar_id) {
        this.calendar_id = calendar_id;
    }
}