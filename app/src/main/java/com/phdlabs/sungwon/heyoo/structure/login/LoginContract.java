package com.phdlabs.sungwon.heyoo.structure.login;

import com.phdlabs.sungwon.heyoo.structure.core.Contract;

/**
 * Created by SungWon on 5/7/2017.
 */

public interface LoginContract {

    interface View extends Contract.BaseView{
//        String getUser();
//        String getPassword();
        void showLogin();

        void showRegister();

        void showLoginError();

        void showPasswordError();

        void sendSMS();

    }

    interface Controller extends Contract.BaseController{
        void onLoginClicked();

        void onRegisterClicked();
    }
}
