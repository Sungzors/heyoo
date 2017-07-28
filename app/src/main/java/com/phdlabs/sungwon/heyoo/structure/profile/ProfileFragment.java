package com.phdlabs.sungwon.heyoo.structure.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.UserManager;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

import static android.content.ContentValues.TAG;

/**
 * Created by SungWon on 7/27/2017.
 */

public class ProfileFragment extends BaseFragment<ProfileContract.Controller>
        implements ProfileContract.View, View.OnClickListener{

    FrameLayout mProfilePic;
    EditText mFirstName;
    EditText mLastName;
    EditText mLocation;
    FrameLayout mFB;
    Button mNextButton;

    UserManager mUserManager;

    public static ProfileFragment newInstance(){
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected ProfileContract.Controller createController() {
        return new ProfileController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onStart() {
        super.onStart();
        int i = mUserManager.getUser().getId();
        Log.d(TAG, "onStart: poo" + i);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfilePic = findById(R.id.fp_profile_pic);
        mFirstName = findById(R.id.fp_firstname_input);
        mLastName = findById(R.id.fp_lastname_input);
        mLocation = findById(R.id.fp_location_input);
        mFB = findById(R.id.fp_facebook);
        mNextButton = findById(R.id.fp_next_button);
        mUserManager = UserManager.getInstance();
        Log.d(TAG, "onViewCreated: poo");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fp_next_button:

                break;
        }
    }
}
