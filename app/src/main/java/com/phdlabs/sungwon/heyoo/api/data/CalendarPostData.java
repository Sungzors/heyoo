package com.phdlabs.sungwon.heyoo.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.phdlabs.sungwon.heyoo.model.User;

import java.util.List;

/**
 * Created by SungWon on 6/14/2017.
 */

public class CalendarPostData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("color")
    @Expose
    private String color;
    private List<User> users;

    public CalendarPostData(String name, String color, List<User> users) {
        this.name = name;
        this.color = color;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
