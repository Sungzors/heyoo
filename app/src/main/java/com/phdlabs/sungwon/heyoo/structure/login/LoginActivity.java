package com.phdlabs.sungwon.heyoo.structure.login;

import android.os.Bundle;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseActivity;

/**
 * Created by SungWon on 5/5/2017.
 */

public class LoginActivity extends BaseActivity {

    LoginFragment mLoginFragment;
    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int contentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoginFragment();
    }

    private void showLoginFragment(){
        replaceFragment(new LoginFragment(), false);
    }
}
