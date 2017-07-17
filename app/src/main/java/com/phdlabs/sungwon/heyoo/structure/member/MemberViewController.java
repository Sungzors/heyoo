package com.phdlabs.sungwon.heyoo.structure.member;

/**
 * Created by SungWon on 7/16/2017.
 */

public class MemberViewController implements MemberViewContract.Controller {

    MemberViewContract.View mView;

    MemberViewController(MemberViewContract.View mView){
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
