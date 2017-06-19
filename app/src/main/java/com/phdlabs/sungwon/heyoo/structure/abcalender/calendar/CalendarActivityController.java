package com.phdlabs.sungwon.heyoo.structure.abcalender.calendar;

import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainController;

/**
 * Created by SungWon on 6/12/2017.
 */

public class CalendarActivityController extends MainController<CalendarContract.Activity.View>
        implements CalendarContract.Activity.Controller{

    public CalendarActivityController(CalendarContract.Activity.View view){
        super(view);
    }
}
