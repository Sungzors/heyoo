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
        List<HeyooAttendee> list = getDummyAttendee();
        List<HeyooAttendee> asslist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getEventidlist().contains(mView.getEventid())){
                asslist.add(list.get(i));
            }
        }
        return asslist;
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

    public List<HeyooAttendee> getDummyAttendee(){
        List<HeyooAttendee> list = new ArrayList<>();
        HeyooAttendee guy1 = new HeyooAttendee(0, "Guy", "One", "http://www.uni-regensburg.de/Fakultaeten/phil_Fak_II/Psychologie/Psy_II/beautycheck/english/prototypen/m_sexy_gr.jpg","going", "Heyoo Member");
        HeyooAttendee guy2 = new HeyooAttendee(1, "Guy", "Two", "http://static.tvtropes.org/pmwiki/pub/images/AverageMan1.jpg", "maybe", "Facebook Member");
        HeyooAttendee girl1 = new HeyooAttendee(2, "Girl", "One", "http://www.uni-regensburg.de/Fakultaeten/phil_Fak_II/Psychologie/Psy_II/beautycheck/english/prototypen/w_sexy_gr.jpg", "no", "Heyoo Member");
        List<Integer> guy1list = new ArrayList<>();
        guy1list.add(0);
        guy1list.add(2);
        List<Integer> guy2list = new ArrayList<>();
        guy2list.add(1);
        List<Integer> girl1list = new ArrayList<>();
        girl1list.add(0);
        girl1list.add(1);
        girl1list.add(2);
        guy1.setEventidlist(guy1list);
        guy2.setEventidlist(guy2list);
        girl1.setEventidlist(girl1list);
        list.add(guy1);
        list.add(guy2);
        list.add(girl1);
        return list;
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
