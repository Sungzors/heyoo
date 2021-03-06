package com.phdlabs.sungwon.heyoo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by SungWon on 4/25/2017.
 */

public class HeyooCalendar implements Serializable{

    private int id;
    private String name;
    private String color;
    private int owner_id;
    private Date created_at;
    private Date updated_at;
    private transient List<HeyooEvent> eventsList;
    private int user_id;
    private List<HeyooAttendee> users;


    public List<HeyooEvent> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<HeyooEvent> eventsList) {
        this.eventsList = eventsList;
    }

    public HeyooCalendar(int id, String name, String color, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public HeyooCalendar(int id, String name, String color, int owner_id, Date created_at, Date updated_at, int user_id) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.owner_id = owner_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user_id = user_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public List<HeyooAttendee> getUsers() {
        return users;
    }

    public void setUsers(List<HeyooAttendee> users) {
        this.users = users;
    }
}
