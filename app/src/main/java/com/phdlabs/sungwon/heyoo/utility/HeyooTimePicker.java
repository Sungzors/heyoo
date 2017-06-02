package com.phdlabs.sungwon.heyoo.utility;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SungWon on 5/31/2017.
 */

public class HeyooTimePicker implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context ctx;
    private String dayString = "";
    private int year;
    private int month;
    private int day;

    public HeyooTimePicker(EditText editText, Context ctx){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();
        this.ctx = ctx;
    }

    public HeyooTimePicker(EditText editText, Context ctx, int year, int month, int day){
        this.editText = editText;
        this.myCalendar = Calendar.getInstance();
        this.ctx = ctx;
        this.year = year;
        this.month = month;
        this.day = day;
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        new TimePickerDialog(ctx, this, hour, minute, false).show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);
            new TimePickerDialog(ctx, this, hour, minute, true).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myCalendar.set(year, month, day, hourOfDay, minute);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa");
        sdf.setTimeZone(myCalendar.getTimeZone());
        editText.setText(sdf.format(myCalendar.getTime()));
        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public Calendar getMyCalendar(){
        return myCalendar;
    }

}
