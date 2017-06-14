package com.phdlabs.sungwon.heyoo.model;

import android.util.SparseArray;

import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;

/**
 * Created by SungWon on 4/25/2017.
 */

public class HeyooCalendarManager {

    private static HeyooCalendarManager mInstance;
    private final HeyooEndpoint mHeyooEndpoint;

    private SparseArray<HeyooEvent> mMap;

    public static HeyooCalendarManager getInstance(){
        if (mInstance == null) {
            mInstance = new HeyooCalendarManager();
        }
        return mInstance;
    }

    private HeyooCalendarManager(){
        super();
        mHeyooEndpoint = Rest.getInstance().getHeyooEndpoint();
        mMap = new SparseArray<>();
    }


}
