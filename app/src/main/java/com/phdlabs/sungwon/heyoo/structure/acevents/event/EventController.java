package com.phdlabs.sungwon.heyoo.structure.acevents.event;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendarManager;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/22/2017.
 */

public class EventController implements EventContract.Controller{

    private static final String TAG = "EventController";

    private EventContract.View mView;

    private HeyooCalendarManager mCalendarManager;
    private HeyooEventManager mEventManager;

    EventController(EventContract.View mView){
        this.mView = mView;
    }

    @Override
    public void onStart() {
        mCalendarManager = HeyooCalendarManager.getInstance(new Preferences(getContext()).getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null));
        mEventManager = HeyooEventManager.getInstance();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onEdit() {
        mView.showEdit();
    }

    @Override
    public void onOverviewClicked() {

    }

    @Override
    public void onDiscussClicked() {

    }

    @Override
    public void onTitleYesClicked() {
        Log.d(TAG, "Yes on Title Clicked");
    }

    @Override
    public void onTitleMaybeClicked() {
        Log.d(TAG, "Maybe on Title Clicked");
    }

    @Override
    public void onTitleNoClicked() {
        Log.d(TAG, "No on Title Clicked");
    }

    @Override
    public void onMediaAddClicked() {
        mView.showMediaAdd();
    }

    @Override
    public void onMediaPictureClicked() {

    }

    @Override
    public void onSummaryReminderClicked() {

    }

    @Override
    public void onAttendeeClicked(HeyooAttendee attendee) {
        Toast.makeText(getContext(), "Clicked " + attendee.getFirst_name() + attendee.getLast_name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttendeeAddClicked() {
        mView.showAttendeeAdd();
    }

    @Override
    public void onAttachmentClicked() {

    }

    @Override
    public void onAttachmentAddClicked() {

    }

    @Override
    public List<HeyooAttendee> getAssociatedAttendees() {
        return mView.getEvent().getAttendees();
    }

    @Override
    public List<HeyooMedia> getAssociatedMedia() {
        List<HeyooMedia> list = new ArrayList<>();
        if(mView.getEventid()!= -1){
            list = mEventManager.getEvent(mView.getEventid()).getMedia();
        }
        List<HeyooMedia> asslist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getEvent_id() == mView.getEventid()){
                asslist.add(list.get(i));
            }
        }
        return asslist;
    }


    @Override
    public List<HeyooCalendar> getSelectedCalendar() {
        List<HeyooCalendar> list = new ArrayList<>();
        list.add(mCalendarManager.getCalendar(mView.getEvent().getCalendars()));
        return list;
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }
}
