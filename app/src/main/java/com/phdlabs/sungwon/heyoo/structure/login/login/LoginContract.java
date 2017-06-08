package com.phdlabs.sungwon.heyoo.structure.login.login;

import com.phdlabs.sungwon.heyoo.structure.core.Contract;

/**
 * Created by SungWon on 5/7/2017.
 */

public interface LoginContract {

    interface View extends Contract.BaseView{
        String getEmail();
        String getCountryCode();
        String getPhone();

        void showLogin();

        void showRegister();

        void showVerify(String phone, String country_code);

        void showLoginError();

        void showPasswordError();

        void sendSMS();

    }

    interface Controller extends Contract.BaseController{
        void onLoginClicked();

        void onRegisterClicked();
    }

    interface Register{
        interface View extends Contract.BaseView {
            String getCode();
            String getPhone();
            String getCountry();

            void openApp();

            void cancel();
        }

        interface Controller extends Contract.BaseController {
            void onResend();

            void onRegister(String code, String phone);

        }
    }
}
