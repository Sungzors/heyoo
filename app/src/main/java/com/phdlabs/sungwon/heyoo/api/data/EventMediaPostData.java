package com.phdlabs.sungwon.heyoo.api.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.RequestBody;

/**
 * Created by SungWon on 6/28/2017.
 */

public class EventMediaPostData {
    @SerializedName("file")
    @Expose
    private RequestBody file;
    @SerializedName("name")
    @Expose
    private String name;

    public EventMediaPostData(RequestBody file, @Nullable String name) {
        this.file = file;
        this.name = name;
    }
}
