package com.phdlabs.sungwon.heyoo.structure.aahome.eventedit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.Constants;

/**
 * Created by SungWon on 5/30/2017.
 */

public class EventEditFragment extends BaseFragment<EventEditContract.Controller>
        implements EventEditContract.View {


    private HeyooEvent mEvent;

    public static EventEditFragment newInstance(HeyooEvent event){
        Bundle args = new Bundle();
        args.putSerializable(Constants.BundleKeys.EVENT_DETAIL, event);
        EventEditFragment fragment = new EventEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected EventEditContract.Controller createController() {
        return new EventEditController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_edit_event;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        mEvent = (HeyooEvent) args.getSerializable(Constants.BundleKeys.EVENT_DETAIL);
        getBaseActivity().setToolbarTitle(mEvent.getName());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showEventEdit() {

    }

    @Override
    public void hideProgress() {

    }

}

