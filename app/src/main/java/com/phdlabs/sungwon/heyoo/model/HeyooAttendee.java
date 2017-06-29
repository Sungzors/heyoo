package com.phdlabs.sungwon.heyoo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/22/2017.
 */

public class HeyooAttendee implements Serializable{

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String country_code;
    private String profile_picture;
    private String avatar;
    private String status;
    private String passcode;
    private String passcode_expire;
    private boolean verified;
    private String city;
    private List<Integer> eventidlist = new ArrayList<>();

    public HeyooAttendee(int id, String first_name, String last_name, String profile_picture, String status, String avatar) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_picture = profile_picture;
        this.status = status;
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getEventidlist() {
        return eventidlist;
    }

    public void setEventidlist(List<Integer> eventidlist) {
        this.eventidlist = eventidlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
