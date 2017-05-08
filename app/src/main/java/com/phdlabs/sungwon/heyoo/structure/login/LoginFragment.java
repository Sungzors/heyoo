package com.phdlabs.sungwon.heyoo.structure.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 5/7/2017.
 */

public class LoginFragment extends BaseFragment<LoginContract.Controller>
    implements LoginContract.View{

    @NonNull
    @Override
    protected LoginContract.Controller createController() {
        return new LoginController(getContext(), this);
    }

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLogin() {

    }

    @Override
    public void showRegister() {

    }

    @Override
    public void showLoginError() {

    }

    @Override
    public void showPasswordError() {

    }

    @Override
    public void sendSMS() {

    }
}
