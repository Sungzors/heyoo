package com.phdlabs.sungwon.heyoo.structure.aahome.event;

import android.content.Context;

import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.structure.core.Contract;

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
        void onEdit();

        void onOverviewClicked();

        void onDiscussClicked();

        void onTitleYesClicked();
        void onTitleMaybeClicked();
        void onTitleNoClicked();

        void onMediaAddClicked();
        void onMediaPictureClicked();

        void onSummaryReminderClicked();

        void onAttendeeClicked(HeyooAttendee attendee);

        void onAttachmentClicked();
        void onAttachmentAddClicked();

        List<HeyooAttendee> getAssociatedAttendees();

        List<HeyooMedia> getAssociatedMedia();

        Context getContext();

    }
}

