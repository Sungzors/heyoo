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
    private HeyooAttendeeStatusNest attendees;
    private transient boolean isChecked = false;

    public HeyooAttendee(int id, String first_name, String last_name, String profile_picture, HeyooAttendeeStatusNest attendees, String avatar) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_picture = profile_picture;
        this.attendees = attendees;
        this.avatar = avatar;
    }

    public HeyooAttendee(String first_name, String last_name, String phone, String country_code, boolean verified) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.country_code = country_code;
        this.verified = verified;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HeyooAttendeeStatusNest getAttendees() {
        return attendees;
    }

    public void setAttendees(HeyooAttendeeStatusNest attendees) {
        this.attendees = attendees;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
