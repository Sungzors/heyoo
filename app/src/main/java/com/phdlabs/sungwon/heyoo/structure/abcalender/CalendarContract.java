package com.phdlabs.sungwon.heyoo.structure.abcalender;

import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainContract;

/**
 * Created by SungWon on 6/12/2017.
 */

public interface CalendarContract {

    interface Activity {
        interface View extends MainContract.View{}
        interface Controller extends MainContract.Controller{}
    }

    interface View {

    }

    interface Controller {

    }
}
