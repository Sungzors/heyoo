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
import com.phdlabs.sungwon.heyoo.structure.login.register.RegisterFragment;

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
    TextView mLoginTitle;
    TextView mLoginEmailText;


    boolean isRegister = true;

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
        isRegister = true;
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
        mLoginTitle = findById(R.id.login_title);
        mLoginEmailText = findById(R.id.login_email_text);
        mLoginButton.setOnClickListener(this);
        mLoginSigned.setOnClickListener(this);
        showRegister();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLogin() {
        mLoginEmailText.setVisibility(View.GONE);
        mLoginEmail.setVisibility(View.GONE);
        mLoginTitle.setText(R.string.login);
        mLoginButton.setText(R.string.sign_in);
        mLoginSigned.setText(R.string.register_inquiry);
    }

    @Override
    public void showRegister() {
        mLoginEmailText.setVisibility(View.VISIBLE);
        mLoginEmail.setVisibility(View.VISIBLE);
        mLoginTitle.setText(R.string.create_your_account);
        mLoginButton.setText(R.string.sign_up);
        mLoginSigned.setText(R.string.sign_in_inquiry);
    }


    @Override
    public void showVerify(String phone, String country_code) {
        getBaseActivity().replaceFragment(RegisterFragment.newInstance(phone, country_code), true);
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
                controller.onRegisterClicked(isRegister);
                break;
            case R.id.login_already_sign:
                isRegister = !isRegister;
                if (isRegister){
                    showRegister();
                } else {
                    showLogin();
                }
                break;
        }
    }
}
