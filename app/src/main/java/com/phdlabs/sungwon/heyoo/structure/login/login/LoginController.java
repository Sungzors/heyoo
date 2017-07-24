package com.phdlabs.sungwon.heyoo.structure.login.login;

import android.content.Context;
import android.widget.Toast;

import com.phdlabs.sungwon.heyoo.api.data.LoginData;
import com.phdlabs.sungwon.heyoo.api.data.ResendData;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.event.RegisterDataEvent;
import com.phdlabs.sungwon.heyoo.api.event.RegisterResendCodeEvent;
import com.phdlabs.sungwon.heyoo.api.response.ResendResponse;
import com.phdlabs.sungwon.heyoo.api.response.UserDataResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by SungWon on 5/7/2017.
 */

public class LoginController implements LoginContract.Controller {

    private Context mContext;
    private LoginContract.View mView;
    private HeyooEndpoint mCaller;
    private EventBus mEvents;

    public LoginController(Context context, LoginContract.View view){
        this.mContext = context;
        this.mView = view;
        this.mCaller = Rest.getInstance().getHeyooEndpoint();
        this.mEvents = EventsManager.getInstance().getDataEventBus();

    }

    @Subscribe
    public void onEventMainThread(RegisterDataEvent event){
        Toast.makeText(mContext, event.getErrorMessage(), Toast.LENGTH_SHORT).show();

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

    @Override
    public void onLoginClicked() {

    }

    @Override
    public void onRegisterClicked(boolean isRegister) {

        mView.showProgress();
        if(isRegister){
            LoginData data = new LoginData(mView.getPhone(), mView.getCountryCode(), mView.getEmail());
            Call<UserDataResponse> call = mCaller.register(data);
            call.enqueue(new HCallback<UserDataResponse, RegisterDataEvent>(mEvents) {
                @Override
                protected void onSuccess(UserDataResponse data) {
                    mView.hideProgress();
                    mView.showVerify(mView.getPhone(), mView.getCountryCode());
                    mEvents.post(new RegisterDataEvent());
                }

                @Override
                protected void onError(Response<UserDataResponse> response) {
                    super.onError(response);

                }
            });
        } else {
            ResendData data = new ResendData(mView.getPhone(), mView.getCountryCode());
            Call<ResendResponse> call = mCaller.resend(data);
            call.enqueue(new HCallback<ResendResponse, RegisterResendCodeEvent>(mEvents) {
                @Override
                protected void onSuccess(ResendResponse data) {
                    mView.hideProgress();
                    mView.showVerify(mView.getPhone(), mView.getCountryCode());
                    mEvents.post(new RegisterResendCodeEvent());
                }
            });
        }

//        mView.showVerify(mView.getPhone(), mView.getCountryCode());
    }
}
