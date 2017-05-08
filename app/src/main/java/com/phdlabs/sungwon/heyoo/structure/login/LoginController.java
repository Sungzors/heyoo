package com.phdlabs.sungwon.heyoo.structure.login;

import android.content.Context;

/**
 * Created by SungWon on 5/7/2017.
 */

public class LoginController implements LoginContract.Controller {

    private Context mContext;
    private LoginContract.View mView;

    public LoginController(Context context, LoginContract.View view){
        this.mContext = context;
        this.mView = view;
    }

    public void onEventMainThread(){

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
    public void onRegisterClicked() {

    }
}
