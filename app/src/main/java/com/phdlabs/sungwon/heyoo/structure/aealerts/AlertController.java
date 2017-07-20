package com.phdlabs.sungwon.heyoo.structure.aealerts;

/**
 * Created by SungWon on 7/20/2017.
 */

public class AlertController implements AlertContract.Controller {

    AlertContract.View mView;

    AlertController(AlertContract.View mView){
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
