package com.phdlabs.sungwon.heyoo.structure.acevents.eventdraft;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.acevents.event.EventContract;

/**
 * Created by SungWon on 6/5/2017.
 */

public class EventDraftController implements EventContract.Draft.Controller {

    private EventContract.Draft.View mView;

    EventDraftController(EventContract.Draft.View mView){
        this.mView = mView;
    }

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

    @Override
    public void onDraftSelected(HeyooEvent event) {
        mView.showSelectedDraft(event);
    }
}
