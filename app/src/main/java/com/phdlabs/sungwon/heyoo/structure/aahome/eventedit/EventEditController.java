package com.phdlabs.sungwon.heyoo.structure.aahome.eventedit;

import android.content.Context;

import com.phdlabs.sungwon.heyoo.utility.adapter.EventEditRecyclerAdapter;

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
    public void onSaveDraftClicked(EventEditRecyclerAdapter adapter) {

    }

    @Override
    public void onPublishClicked(EventEditRecyclerAdapter adapter) {

    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public void onStop() {

    }
}
