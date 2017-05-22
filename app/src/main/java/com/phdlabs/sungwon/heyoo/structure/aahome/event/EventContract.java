package com.phdlabs.sungwon.heyoo.structure.aahome.event;

import com.phdlabs.sungwon.heyoo.structure.core.Contract;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainContract;

/**
 * Created by SungWon on 5/19/2017.
 */

public interface EventContract {

    interface Activity {
        interface View extends MainContract.View{}
        interface Controller extends MainContract.Controller{}
    }

    interface View extends Contract.BaseView{
        int getEventid();
    }

    interface Controller extends Contract.BaseController{
        void onOverviewClicked();

        void onDiscussClicked();

        void onTitleYesClicked();
        void onTitleMaybeClicked();
        void onTitleNoClicked();

        void onMediaAddClicked();
        void onMediaPictureClicked();

        void onSummaryReminderClicked();

        void onAttachmentClicked();

    }
}

