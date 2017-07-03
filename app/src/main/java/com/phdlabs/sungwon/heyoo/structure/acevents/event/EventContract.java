package com.phdlabs.sungwon.heyoo.structure.acevents.event;

import android.content.Context;

import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.structure.core.Contract;

import java.util.List;

/**
 * Created by SungWon on 5/19/2017.
 */

public interface EventContract {



    interface View extends Contract.BaseView{
        int getEventid();

        HeyooEvent getEvent();

        void showEventDetails();

        void showEdit();

        void showMediaAdd();
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

        List<HeyooCalendar> getSelectedCalendar();

    }

    interface Draft {
        interface View extends Contract.BaseView {
            void showDrafts();

            void showSelectedDraft(HeyooEvent event);
        }

        interface Controller extends Contract.BaseController {
            void onDraftSelected(HeyooEvent event);
        }
    }
}

