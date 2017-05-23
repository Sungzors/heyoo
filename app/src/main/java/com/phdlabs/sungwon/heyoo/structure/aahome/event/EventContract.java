package com.phdlabs.sungwon.heyoo.structure.aahome.event;

import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.structure.core.Contract;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainContract;

import java.util.List;

/**
 * Created by SungWon on 5/19/2017.
 */

public interface EventContract {



    interface View extends Contract.BaseView{
        int getEventid();

        void showEventDetails();
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

        List<HeyooAttendee> getAssociatedAttendees();

        List<HeyooMedia> getAssociatedMedia();

    }
}

