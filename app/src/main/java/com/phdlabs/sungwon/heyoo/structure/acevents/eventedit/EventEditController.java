package com.phdlabs.sungwon.heyoo.structure.acevents.eventedit;

import android.content.Context;

import com.phdlabs.sungwon.heyoo.api.event.CalendarRetrievalEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendarManager;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/30/2017.
 */

public class EventEditController implements EventEditContract.Controller {

    private static final String TAG = "EventEditController";

    private EventEditContract.View mView;
    private HeyooEventManager mEventManager;
    private HeyooCalendarManager mCalendarManager;

    EventEditController(EventEditContract.View mView){
        this.mView = mView;
    }

    @Override
    public void onStart() {
        mCalendarManager = HeyooCalendarManager.getInstance(new Preferences(getContext()).getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null));
        mCalendarManager.getEventBus().register(this);
        mCalendarManager.loadCalendars();
        mEventManager = HeyooEventManager.getInstance();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }


    @Subscribe
    public void onEventMainThread(CalendarRetrievalEvent event){
        if (event.isSuccess()){
            mView.showEventEdit();
        } else {
            mView.showError(event.getErrorMessage());
        }
    }

    @Override
    public void onMediaAddClicked() {
        mView.openMediaAdd();
    }

    @Override
    public void onSaveDraftClicked() {
        mView.saveEvent();
    }

    @Override
    public void onPublishClicked() {
        mView.postEvent();
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
    public List<HeyooCalendar> getCalendars() {
        return mCalendarManager.getCalendars();
    }

    @Override
    public int getSelectedPosition(int calID) {
        return mCalendarManager.getPosition(calID);
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public int getEventid() {
        return mView.getEventid();
    }

    @Override
    public void onStop() {
        mCalendarManager.getEventBus().unregister(this);
    }
}
