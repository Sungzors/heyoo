package com.phdlabs.sungwon.heyoo.utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by SungWon on 5/31/2017.
 */

public class HeyooDatePicker implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private TextView textView;
    private Calendar myCalendar;
    private Context ctx;
    private String dayString = "";
    HeyooTimePicker timePicker;
    public boolean isFinished = false;

    public HeyooDatePicker(TextView textView, Context ctx){
        this.textView = textView;
        this.textView.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();
        this.ctx = ctx;

        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(ctx, this, year, month, day).show();
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            int year = myCalendar.get(Calendar.YEAR);
            int month = myCalendar.get(Calendar.MONTH);
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(ctx, this, year, month, day).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        isFinished = true;
        timePicker = new HeyooTimePicker(textView, ctx, year, month, day);
    }

    public HeyooTimePicker getTimePicker(){
        return timePicker;
    }
}
