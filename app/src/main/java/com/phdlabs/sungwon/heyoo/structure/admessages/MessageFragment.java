package com.phdlabs.sungwon.heyoo.structure.admessages;

import android.support.annotation.NonNull;

import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 8/1/2017.
 */

public class MessageFragment extends BaseFragment<MessageContract.Controller>
        implements MessageContract.View{

    @NonNull
    @Override
    protected MessageContract.Controller createController() {
        return new MessageController(this);
    }

    @Override
    protected int layoutId() {
        return 0;
    }
}
