package com.phdlabs.sungwon.heyoo.structure.login.login;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 5/7/2017.
 */

public class LoginFragment extends BaseFragment<LoginContract.Controller>
    implements LoginContract.View, View.OnClickListener{

    EditText mLoginEmail;
    CountryCodePicker mLoginccp;
    EditText mLoginNumber;
    Button mLoginButton;
    TextView mLoginSigned;

    @NonNull
    @Override
    protected LoginContract.Controller createController() {
        return new LoginController(getContext(), this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mLoginEmail = findById(R.id.login_email);
        mLoginccp = findById(R.id.login_ccp);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/NunitoSans-Bold.ttf");
        mLoginccp.setTypeFace(typeface);
        mLoginNumber = findById(R.id.login_phone_number);
        mLoginButton = findById(R.id.login_sign_button);
        mLoginSigned = findById(R.id.login_already_sign);
        mLoginButton.setOnClickListener(this);
        mLoginSigned.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
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
    public void showVerify() {
//        getBaseActivity().replaceFragment();
    }

    @Override
    public void showPasswordError() {

    }

    @Override
    public void sendSMS() {

    }

    @Override
    public String getEmail() {
        if(TextUtils.isEmpty(mLoginEmail.getText().toString())){
            Toast.makeText(getContext(), "Please enter an Email", Toast.LENGTH_SHORT).show();
        }
        return mLoginEmail.getText().toString();
    }

    @Override
    public String getCountryCode() {
        return mLoginccp.getSelectedCountryCodeWithPlus();
    }

    @Override
    public String getPhone() {
        if(mLoginNumber.getText().toString().length() != 10){
            Toast.makeText(getContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
        return mLoginNumber.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_sign_button:
                controller.onRegisterClicked();
                break;
            case R.id.login_already_sign:
                break;
        }
    }
}
