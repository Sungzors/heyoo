package com.phdlabs.sungwon.heyoo.structure.aahome;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.Contract;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainContract;

import java.util.List;

/**
 * Created by SungWon on 4/18/2017.
 */

public interface HomeContract {

    interface Activity {
        interface View extends MainContract.View{}
        interface Controller extends MainContract.Controller{}
    }


    interface View extends Contract.BaseView{
        void showAddEvent();

        void showEventDetail(HeyooEvent event);

        void showFullCalendar();

        void showPartialCalendar();

        void refreshList();

        void showDraftFragment();

        int getSelectedTab();// TODO: 4/18/2017

        void showEvents(List<HeyooEvent> heyooEvents);

        int getCalendarId();
    }

    interface Controller extends Contract.BaseController{
        void onDaySelected();

        void onDayUnselected();// TODO: 4/18/2017


    }
}
