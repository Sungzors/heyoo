package com.phdlabs.sungwon.heyoo.structure.aahome;

import com.phdlabs.sungwon.heyoo.structure.core.Contract;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainContract;

/**
 * Created by SungWon on 4/18/2017.
 */

public interface HomeContract {

    interface Activity {
        interface View extends MainContract.View{}
        interface Controller extends MainContract.Controller{}
    }

    interface View extends Contract.BaseView{
        void showAddEvent(); // TODO: 4/18/2017

        void showEventDetail();// TODO: 4/18/2017

        void showFullCalendar();// TODO: 4/18/2017  

        void showPartialCalendar();// TODO: 4/18/2017

        int getSelectedTab();// TODO: 4/18/2017  
    }

    interface Controller extends Contract.BaseController{
        void onDaySelected();// TODO: 4/18/2017

        void onDayUnselected();// TODO: 4/18/2017


    }
}
