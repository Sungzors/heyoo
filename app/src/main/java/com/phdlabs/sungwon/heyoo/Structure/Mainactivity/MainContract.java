package com.phdlabs.sungwon.heyoo.Structure.Mainactivity;

import com.phdlabs.sungwon.heyoo.Structure.Contract;

/**
 * Created by SungWon on 4/13/2017.
 */

public interface MainContract {
    interface View extends Contract.BaseView{
        void showHomePage();
        void showCalendarPage();
        void showNewPage();
        void showMessagesPage();
        void showAlertsPage();
    }

    interface Controller extends Contract.BaseController{
        void onHomeSelected();
        void onCalendarSelected();
        void onNewSelected();
        void onMessagesSelected();
        void onAlertsSelected();
    }
}
