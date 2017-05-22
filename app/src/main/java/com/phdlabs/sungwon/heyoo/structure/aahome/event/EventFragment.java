package com.phdlabs.sungwon.heyoo.structure.aahome.event;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.Constants;

/**
 * Created by SungWon on 5/22/2017.
 */

public class EventFragment extends BaseFragment<EventContract.Controller>
        implements EventContract.View{

    private int mEventID;

    public static EventFragment newInstance(int eventid){
        Bundle args = new Bundle();
        args.putInt(Constants.BundleKeys.EVENT_ID, eventid);
        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected EventContract.Controller createController() {
        return new EventController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_event;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        mEventID = args.getInt(Constants.BundleKeys.EVENT_ID, 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public int getEventid() {
        return mEventID;
    }
}
