package com.phdlabs.sungwon.heyoo.structure.acevents.eventedit;

import android.content.Context;

import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.structure.core.Contract;

import java.util.List;

/**
 * Created by SungWon on 5/30/2017.
 */

public interface EventEditContract {

    interface View extends Contract.BaseView{
        void showEventEdit();

        int getEventid();

        void saveEvent();
        void postEvent();

        void openMediaAdd();
    }

    interface Controller extends Contract.BaseController{
        void onPublishClicked();
        void onSaveDraftClicked();

        List<HeyooMedia> getAssociatedMedia();
        List<HeyooCalendar> getCalendars();

        void onMediaAddClicked();

        Context getContext();

        int getEventid();
        int getSelectedPosition(int calID);

    }
}
