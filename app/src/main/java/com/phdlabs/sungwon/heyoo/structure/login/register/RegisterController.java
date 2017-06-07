package com.phdlabs.sungwon.heyoo.structure.login.register;

import com.phdlabs.sungwon.heyoo.api.data.ResendData;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.event.RegisterDataEvent;
import com.phdlabs.sungwon.heyoo.api.response.UserDataResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;
import com.phdlabs.sungwon.heyoo.structure.login.login.LoginContract;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by SungWon on 6/7/2017.
 */

public class RegisterController implements LoginContract.Register.Controller {

    private LoginContract.Register.View mView;
    private HeyooEndpoint mCaller;
    private EventBus mEvents;

    RegisterController(LoginContract.Register.View view){
        this.mView = view;
        this.mCaller = Rest.getInstance().getHeyooEndpoint();
        this.mEvents = EventsManager.getInstance().getDataEventBus();
    }

    @Override
    public void onResend() {
        ResendData data = new ResendData(mView.getPhone());
        Call<UserDataResponse> call = mCaller.resend(data);
        call.enqueue(new HCallback<UserDataResponse, RegisterDataEvent>(mEvents) {
            @Override
            protected void onSuccess(UserDataResponse data) {
                mEvents.post(new RegisterDataEvent());
            }

            @Override
            protected void onError(Response<UserDataResponse> response) {
                super.onError(response);

            }
        });
    }

    @Override
    public void onStart() {

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
}
