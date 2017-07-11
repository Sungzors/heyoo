package com.phdlabs.sungwon.heyoo.model;

import java.io.Serializable;

/**
 * Created by SungWon on 5/22/2017.
 */

public class HeyooMedia implements Serializable{

    private String title;
    private String name;
    private String file_path;
    private int event_id;
    private int author_id;

    public HeyooMedia(String title, String name, String file_path, int event_id) {
        this.title = title;
        this.name = name;
        this.file_path = file_path;
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
