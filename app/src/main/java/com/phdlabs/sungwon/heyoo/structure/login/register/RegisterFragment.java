package com.phdlabs.sungwon.heyoo.structure.login.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.aahome.home.HomeActivity;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.login.login.LoginContract;
import com.phdlabs.sungwon.heyoo.utility.Constants;

/**
 * Created by SungWon on 6/7/2017.
 */

public class RegisterFragment extends BaseFragment<LoginContract.Register.Controller>
        implements LoginContract.Register.View, View.OnClickListener{

    private String mPhone;

    private EditText mCode;
    private TextView mResend;
    private Button mCreate;
    private TextView mCancel;


    public static RegisterFragment newInstance(String phone){
        Bundle args = new Bundle();
        args.putString(Constants.BundleKeys.LOGIN_DETAIL, phone);
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected LoginContract.Register.Controller createController() {
        return new RegisterController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        mPhone = args.getString(Constants.BundleKeys.LOGIN_DETAIL);
        mCode = findById(R.id.fr_confirmation_code);
        mResend = findById(R.id.fr_resend);
        mCreate = findById(R.id.fr_create_button);
        mCancel = findById(R.id.fr_cancel);
        mResend.setOnClickListener(this);
        mCreate.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    @Override
    public String getPhone() {
        return mPhone;
    }

    @Override
    public String getCode() {
        if(TextUtils.isEmpty(mCode.getText().toString())){
            Toast.makeText(getContext(), "Please enter a valid code", Toast.LENGTH_SHORT).show();
        } else if (mCode.getText().toString().length()!=6){
            Toast.makeText(getContext(), "Please enter a valid code", Toast.LENGTH_SHORT).show();
        }
        return mCode.getText().toString();
    }

    @Override
    public void openApp() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void cancel() {
        onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fr_resend:
                controller.onResend();
                break;
            case R.id.fr_create_button:
                controller.onRegister(getCode(), mPhone);
                break;
            case R.id.fr_cancel:
                cancel();
                break;
        }
    }

}
