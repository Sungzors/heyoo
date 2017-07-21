package com.phdlabs.sungwon.heyoo.model;

import com.phdlabs.sungwon.heyoo.api.event.AlertRetrievalEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.response.AlertRetrievalResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by SungWon on 7/18/2017.
 */

public class HeyooAlertManager {
    private static HeyooAlertManager mInstance;
    private HeyooEndpoint mCaller;
    private String mToken;
    private EventBus mEvents;

    private List<HeyooAlert> mAlerts = new ArrayList<>();

    public static HeyooAlertManager getInstance(String token){
        if (mInstance == null) {
            mInstance = new HeyooAlertManager(token);
        }
        return mInstance;
    }

    public static HeyooAlertManager getInstance(){
        return mInstance;
    }

    private HeyooAlertManager(String token){
        super();
        mCaller = Rest.getInstance().getHeyooEndpoint();
        mToken = token;
        mEvents = EventsManager.getInstance().getDataEventBus();
    }

    public List<HeyooAlert> getAlerts() {return mAlerts;}

    public EventBus getmEvents() {
        return mEvents;
    }

    public void loadAlerts(){
        Call<AlertRetrievalResponse> call = mCaller.getAlerts(mToken);
        call.enqueue(new HCallback<AlertRetrievalResponse, AlertRetrievalEvent>(mEvents) {
            @Override
            protected void onSuccess(AlertRetrievalResponse data) {
                mAlerts.clear();
                mAlerts.addAll(data.getAlerts());
                mEvents.post(new AlertRetrievalEvent());
            }
        });
    }



}
