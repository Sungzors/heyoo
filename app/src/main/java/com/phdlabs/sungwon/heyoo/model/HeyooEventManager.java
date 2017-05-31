package com.phdlabs.sungwon.heyoo.model;

import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/8/2017.
 */

public class HeyooEventManager {
    private static HeyooEventManager mInstance;
    private final HeyooEndpoint mHeyooEndpoint;

    private List<HeyooEvent> mList;

    public static HeyooEventManager getInstance(){
        if (mInstance == null) {
            mInstance = new HeyooEventManager();
        }
        return mInstance;
    }

    private HeyooEventManager(){
        super();
        mHeyooEndpoint = Rest.getInstance().getHeyooEndpoint();
        mList = new ArrayList<>();
    }

    public List<HeyooEvent> getEvents(){
        return mList;
    }

    public void addEvents(HeyooEvent event){
        mList.add(event);
    }
}
