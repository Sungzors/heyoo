package com.phdlabs.sungwon.heyoo.structure.aahome.eventedit;

import android.content.Context;

/**
 * Created by SungWon on 5/30/2017.
 */

public class EventEditController implements EventEditContract.Controller {

    private static final String TAG = "EventEditController";

    private EventEditContract.View mView;

    EventEditController(EventEditContract.View mView){
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
    public void onSaveDraft() {

    }

    @Override
    public void onPublishClicked() {

    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public void onStop() {

    }
}
