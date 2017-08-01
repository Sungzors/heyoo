package com.phdlabs.sungwon.heyoo.structure.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.data.UserPatchData;
import com.phdlabs.sungwon.heyoo.api.event.AvatarPostEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.event.UserPatchEvent;
import com.phdlabs.sungwon.heyoo.api.response.UserPatchResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;
import com.phdlabs.sungwon.heyoo.model.User;
import com.phdlabs.sungwon.heyoo.model.UserManager;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.CameraControl;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    HeyooEndpoint mCaller;
    EventBus mEvents;

    private Uri mCapturedImageURI;
    private InputStream mProfilePictureInputStream;

    private boolean hasProfilePic = false;



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
        mProfilePic.setOnClickListener(this);
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
                mCaller = Rest.getInstance().getHeyooEndpoint();
                mEvents = EventsManager.getInstance().getDataEventBus();
                if(hasProfilePic){
                    byte[] buff = new byte[8000];
                    int bytesRead = 0;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        while((bytesRead = mProfilePictureInputStream.read(buff)) != -1){
                            bos.write(buff, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] byteArray = bos.toByteArray();
                    RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("file", "heyoo" + System.currentTimeMillis(), RequestBody.create(MediaType.parse("image/png"),byteArray))
                            .build();
                    Call<UserPatchResponse> call2 = mCaller.postAvatar(mUserManager.getUser().getId(), mUserManager.getToken(), formBody);
                    call2.enqueue(new HCallback<UserPatchResponse, AvatarPostEvent>(mEvents) {
                        @Override
                        protected void onSuccess(UserPatchResponse data) {
                            mEvents.post(new AvatarPostEvent());
                        }
                    });
                }
                Call<UserPatchResponse> call = mCaller.postProfile(mUserManager.getUser().getId(), mUserManager.getToken(), getData());
                call.enqueue(new HCallback<UserPatchResponse, UserPatchEvent>(mEvents) {
                    @Override
                    protected void onSuccess(UserPatchResponse data) {
                        mEvents.post(new UserPatchEvent());
                        getBaseActivity().onBackPressed();
                    }
                });

                break;
            case R.id.fp_profile_pic:
                CameraControl.chooseProfilePicture(getBaseActivity());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCapturedImageURI = CameraControl.getPictureUri(getContext(), requestCode, resultCode, data);
        if (mCapturedImageURI != null) {
            //Get temporary Input Stream
            mProfilePictureInputStream = CameraControl.
                    getInputStreamFromResult(getContext(), requestCode, resultCode, data);


            //Dev
            Log.i(TAG, "onActivityResult: PICTURE URI: " + mCapturedImageURI);
            Picasso.with(getContext()).load(mCapturedImageURI).placeholder(R.drawable.pandapic).into((ImageView) findById(R.id.dsp_photo));
            findById(R.id.dsp_plus).setVisibility(View.GONE);
            hasProfilePic = true;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
