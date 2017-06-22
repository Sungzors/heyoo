package com.phdlabs.sungwon.heyoo.model;

import com.phdlabs.sungwon.heyoo.api.data.CalendarPostData;
import com.phdlabs.sungwon.heyoo.api.event.CalendarPostEvent;
import com.phdlabs.sungwon.heyoo.api.event.CalendarRetrievalEvent;
import com.phdlabs.sungwon.heyoo.api.response.CalendarPostResponse;
import com.phdlabs.sungwon.heyoo.api.response.CalendarRetrievalResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by SungWon on 4/25/2017.
 */

public class HeyooCalendarManager {

    private static HeyooCalendarManager mInstance;
    private final HeyooEndpoint mCaller;
    private String mToken;
    private EventBus mEvents;

    private List<HeyooCalendar> mCalendars;


    public static HeyooCalendarManager getInstance(String token){
        if (mInstance == null) {
            mInstance = new HeyooCalendarManager(token);
        }
        return mInstance;
    }

    public static HeyooCalendarManager getInstance(){
        return mInstance;
    }

    private HeyooCalendarManager(String token){
        super();
        mCaller = Rest.getInstance().getHeyooEndpoint();
        mToken = token;
        mEvents = new EventBus();
        mCalendars = new ArrayList<>();
    }

    public void loadCalendars(){
        Call<CalendarRetrievalResponse> call = mCaller.getCalendar(mToken);
        call.enqueue(new HCallback<CalendarRetrievalResponse, CalendarRetrievalEvent>(mEvents) {
            @Override
            protected void onSuccess(CalendarRetrievalResponse data) {
                bindCalendars(data.getCalendar());
                mEvents.post(new CalendarRetrievalEvent());
            }
        });
    }

    public void postCalendars(String name, String color, List<User> users){
        CalendarPostData data = new CalendarPostData(name, color, users);
        Call<CalendarPostResponse> call = mCaller.postCalendar(mToken, data);
        call.enqueue(new HCallback<CalendarPostResponse, CalendarPostEvent>(mEvents) {
            @Override
            protected void onSuccess(CalendarPostResponse data) {
                mCalendars.add(data.getCalendar());
                mEvents.post(new CalendarPostEvent());
            }
        });
    }

    private void bindCalendars(List<HeyooCalendar> list){
        if(!mCalendars.isEmpty()){
            mCalendars.clear();
        }
        mCalendars.addAll(list);
    }

    public EventBus getEventBus(){
        return mEvents;
    }

    public List<HeyooCalendar> getCalendars() {
        return mCalendars;
    }

    public HeyooCalendar getCalendar(int calID){
        for (int i = 0; i < mCalendars.size(); i++) {
            if(mCalendars.get(i).getId()==calID){
                return mCalendars.get(i);
            }
        }
        return null;
    }

    public int getPosition(int calID){
        for (int i = 0; i < mCalendars.size(); i++) {
            if(mCalendars.get(i).getId()==calID){
                return i;
            }
        }
        return -1;
    }
}
