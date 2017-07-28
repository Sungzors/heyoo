package com.phdlabs.sungwon.heyoo.structure.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.data.UserPatchData;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.event.UserPatchEvent;
import com.phdlabs.sungwon.heyoo.api.response.UserPatchResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;
import com.phdlabs.sungwon.heyoo.model.User;
import com.phdlabs.sungwon.heyoo.model.UserManager;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

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
        mNextButton.setOnClickListener(this);
        mUserManager = UserManager.getInstance();
        Log.d(TAG, "onViewCreated: poo");
    }

    private UserPatchData getData(){
        String firstname = null;
        if(!TextUtils.isEmpty(mFirstName.getText().toString())){
            firstname = mFirstName.getText().toString();
        }
        String lastname = null;
        if(!TextUtils.isEmpty(mLastName.getText().toString())){
            lastname = mLastName.getText().toString();
        }
        String email;

        User user = mUserManager.getUser();
        return new UserPatchData(firstname, lastname, null, null, null, user.getPhone(), user.getCountry_code());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fp_next_button:
                HeyooEndpoint mCaller = Rest.getInstance().getHeyooEndpoint();
                final EventBus mEvents = EventsManager.getInstance().getDataEventBus();
                Call<UserPatchResponse> call = mCaller.postProfile(mUserManager.getUser().getId(), mUserManager.getToken(), getData());
                call.enqueue(new HCallback<UserPatchResponse, UserPatchEvent>(mEvents) {
                    @Override
                    protected void onSuccess(UserPatchResponse data) {
                        mEvents.post(new UserPatchEvent());
                        getBaseActivity().onBackPressed();
                    }
                });
                break;
        }
    }
}
