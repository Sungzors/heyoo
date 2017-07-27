package com.phdlabs.sungwon.heyoo.structure.profile;

/**
 * Created by SungWon on 7/27/2017.
 */

public class ProfileController implements ProfileContract.Controller{

    private ProfileContract.View mView;

    public ProfileController(ProfileContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
}
