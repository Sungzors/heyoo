package com.phdlabs.sungwon.heyoo.model;

import android.util.SparseArray;

import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by SungWon on 5/8/2017.
 */

public class HeyooEventManager {
    private static HeyooEventManager mInstance;
    private final HeyooEndpoint mHeyooEndpoint;

    private SparseArray<HeyooEvent> mMap;

    public static HeyooEventManager getInstance(){
        if (mInstance == null) {
            mInstance = new HeyooEventManager();
        }
        return mInstance;
    }

    private HeyooEventManager(){
        super();
        mHeyooEndpoint = Rest.getInstance().getHeyooEndpoint();
        mMap = new SparseArray<>();
    }

    public SparseArray<HeyooEvent> getMapEvents(){
        return mMap;
    }

    public void addEvents(HeyooEvent event){
        mMap.put(event.getId(), event);
    }

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
}
