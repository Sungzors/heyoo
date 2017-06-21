package com.phdlabs.sungwon.heyoo.model;

import android.content.Context;
import android.util.SparseArray;

import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.utility.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
//        Call<EventRetrievalResponse> call =
    }
}
