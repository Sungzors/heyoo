package com.phdlabs.sungwon.heyoo.structure.admessages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 8/1/2017.
 */

public class MessageFragment extends BaseFragment<MessageContract.Controller>
        implements MessageContract.View{

    private RecyclerView mRecycler;


    public static MessageFragment newInstance(){
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected MessageContract.Controller createController() {
        return new MessageController(this);
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
