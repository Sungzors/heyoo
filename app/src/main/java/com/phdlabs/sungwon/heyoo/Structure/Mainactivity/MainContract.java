package com.phdlabs.sungwon.heyoo.Structure.Mainactivity;

import com.phdlabs.sungwon.heyoo.Structure.Core.Contract;

/**
 * Created by SungWon on 4/13/2017.
 */

public interface MainContract {
    interface View extends Contract.BaseView{
        void showHomePage();
        void showCalendarPage();
        void showTasksPage();
        void showMessagesPage();
        void showAlertsPage();
    }

    interface Controller extends Contract.BaseController{
        void onHomeSelected();
        void onCalendarSelected();
        void onTasksSelected();
        void onMessagesSelected();
        void onAlertsSelected();
    }
}
