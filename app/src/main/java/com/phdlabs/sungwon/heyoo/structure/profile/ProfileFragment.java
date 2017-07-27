package com.phdlabs.sungwon.heyoo.structure.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 7/27/2017.
 */

public class ProfileFragment extends BaseFragment<ProfileContract.Controller>
        implements ProfileContract.View{

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
        return 0;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
