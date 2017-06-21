package com.phdlabs.sungwon.heyoo.model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.phdlabs.sungwon.heyoo.api.data.EventPatchData;
import com.phdlabs.sungwon.heyoo.api.data.EventPostData;
import com.phdlabs.sungwon.heyoo.api.data.RecurrenceData;
import com.phdlabs.sungwon.heyoo.api.event.EventPatchEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventPostEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventRetrievalEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.response.EventPatchResponse;
import com.phdlabs.sungwon.heyoo.api.response.EventPostResponse;
import com.phdlabs.sungwon.heyoo.api.response.EventRetrievalResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;

/**
 * Created by SungWon on 5/8/2017.
 */

public class HeyooEventManager {
    private static HeyooEventManager mInstance;
    private final HeyooEndpoint mCaller;
    private Context mContext;
    private Preferences mPref;
    private EventBus mEvents;

    private SparseArray<HeyooEvent> mMap;

    public static HeyooEventManager getInstance(Context context){
        if (mInstance == null) {
            mInstance = new HeyooEventManager(context);
        }
        return mInstance;
    }

    private HeyooEventManager(Context context){
        super();
        mCaller = Rest.getInstance().getHeyooEndpoint();
        mMap = new SparseArray<>();
        mContext = context;
        mPref = new Preferences(mContext);
        mEvents = EventsManager.getInstance().getDataEventBus();
    }

    public SparseArray<HeyooEvent> getMapEvents(){
        return mMap;
    }

    public void addEvents(HeyooEvent event){
        mMap.put(event.getId(), event);
    }

    public void setEvents(int key, HeyooEvent event) {mMap.append(key, event);}

    public List<HeyooEvent> getEvents(){
        List<HeyooEvent> eventList = new ArrayList<>();
        for (int i = 0; i < mMap.size(); i++) {
            HeyooEvent event = mMap.valueAt(i);
            if(event.isPublished()){
                eventList.add(event);
            }
        }
        Collections.sort(eventList, new Comparator<HeyooEvent>() {
            @Override
            public int compare(HeyooEvent e1, HeyooEvent e2) {
                return e1.getStart_time().compareTo(e2.getStart_time());
            }
        });
        return eventList;
    }

    public List<HeyooEvent> getUnpublishedEvents(){
        List<HeyooEvent> eventList = new ArrayList<>();
        for (int i = 0; i < mMap.size(); i++) {
            HeyooEvent event = mMap.valueAt(i);
            if(!event.isPublished()){
                eventList.add(event);
            }
        }
        return eventList;
    }

    public void loadEvents(){
        Call<EventRetrievalResponse> call = mCaller.getEvents(mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null));
        call.enqueue(new HCallback<EventRetrievalResponse, EventRetrievalEvent>(mEvents) {
            @Override
            protected void onSuccess(EventRetrievalResponse data) {
                for (int i = 0; i < data.getCalEvents().size(); i++) {
                    mMap.clear();
                    addEvents(data.getCalEvents().get(0));
                }
                mEvents.post(new EventRetrievalEvent());
            }
        });
    }

    public void postEvent(final HeyooEvent event, @Nullable RecurrenceData recur){
        EventPostData data = new EventPostData(event, recur);
        Call<EventPostResponse> call = mCaller.postEvents(mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null), data);
        call.enqueue(new HCallback<EventPostResponse, EventPostEvent>(mEvents) {
            @Override
            protected void onSuccess(EventPostResponse data) {
                addEvents(event);
                mEvents.post(new EventPostEvent());
            }
        });
    }

    public void patchEvent(int eventID, EventPatchData data){
        Call<EventPatchResponse> call = mCaller.patchEvents(eventID, mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null), data);
        call.enqueue(new HCallback<EventPatchResponse, EventPatchEvent>(mEvents) {
            @Override
            protected void onSuccess(EventPatchResponse data) {
                addEvents(data.getCalEvent());
                mEvents.post(new EventPatchEvent());
            }
        });
    }
}
