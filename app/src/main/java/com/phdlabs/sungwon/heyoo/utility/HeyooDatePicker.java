package com.phdlabs.sungwon.heyoo.utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by SungWon on 5/31/2017.
 */

public class HeyooDatePicker implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context ctx;
    private String dayString = "";
    HeyooTimePicker timePicker;

    public HeyooDatePicker(EditText editText, Context ctx){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();
        this.ctx = ctx;
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
        timePicker = new HeyooTimePicker(editText, ctx, year, month, day);
    }

    public HeyooTimePicker getTimePicker(){
        return timePicker;
    }
}
