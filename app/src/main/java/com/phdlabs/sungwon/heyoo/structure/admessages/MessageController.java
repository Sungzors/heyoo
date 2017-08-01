package com.phdlabs.sungwon.heyoo.structure.admessages;

/**
 * Created by SungWon on 8/1/2017.
 */

public class MessageController implements MessageContract.Controller {

    private MessageContract.View mView;

    MessageController(MessageContract.View mView){
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
