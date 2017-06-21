package com.phdlabs.sungwon.heyoo.api.data;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Created by SungWon on 6/21/2017.
 */

class RecurrenceData {
    public static final String WEEKLY = "weekly";
    public static final String MONTHLY = "monthly";

    @StringDef({WEEKLY, MONTHLY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RecurScope{
    }

    private String frequency;
    private int interval;
    private String days;
    private Date start_time;
    private Date end_time;

    public RecurrenceData(@RecurScope String frequency, int interval, String days, Date start_time, Date end_time) {
        this.frequency = frequency;
        this.interval = interval;
        this.days = days;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}
