package com.phdlabs.sungwon.heyoo.structure.aahome.eventedit;

import android.content.Context;

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

    }

    interface Controller extends Contract.BaseController{
        void onPublishClicked();
        void onSaveDraftClicked();

        List<HeyooMedia> getAssociatedMedia();

        void onMediaAddClicked();

        Context getContext();

        int getEventid();

    }
}
