package com.phdlabs.sungwon.heyoo.structure.profile;

import android.os.Bundle;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseActivity;

/**
 * Created by SungWon on 7/27/2017.
 */

public class ProfileActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected int contentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProfileFragment();
    }

    private void showProfileFragment(){
        replaceFragment(ProfileFragment.newInstance(), true);
    }
}
