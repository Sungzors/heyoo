package com.phdlabs.sungwon.heyoo.api.utility;


import com.phdlabs.sungwon.heyoo.api.response.ErrorResponse;
import com.phdlabs.sungwon.heyoo.api.event.Event;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SungWon on 5/8/2017.
 */

public abstract class HCallback<Result, EventClass extends Event> implements Callback<Result>{
    private EventBus eventBus;
    private EventClass eventClass;

    public HCallback(EventBus eventBus, EventClass eventClass) {
        this.eventBus = eventBus;
        this.eventClass = eventClass;
    }

    public HCallback(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    void initParameter() {
        // Get the class name of this instance's type.
        ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
        // You may need this split or not, use logging to check
        String parameterClassName = pt.getActualTypeArguments()[1].toString().split("\\s")[1];
        // Instantiate the Parameter and initialize it.
        try {
            eventClass = (EventClass) Class.forName(parameterClassName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        if (response.isSuccessful()) {
            if (response.body() instanceof ErrorResponse) {
                ErrorResponse r = (ErrorResponse) response.body();
                if (r.isSuccess()) {
                    onSuccess(response.body());
                } else {
                    postErrorMessage(r.getMessage());
                }
            } else {
                onSuccess(response.body());
            }
        } else {
            onError(response);
        }
    }

    protected void onError(Response<Result> response) {
        String message = ErrorResponse.fromResponse(response).getMessage();
        postErrorMessage(message);
    }

    protected abstract void onSuccess(Result data);

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
        String message = t.getMessage();
        postErrorMessage(message);
    }

    private void postErrorMessage(String message) {
        if (eventClass == null) {
            initParameter();
        }
        eventClass.setErrorMessage(message);
        eventBus.post(eventClass);
    }
}
