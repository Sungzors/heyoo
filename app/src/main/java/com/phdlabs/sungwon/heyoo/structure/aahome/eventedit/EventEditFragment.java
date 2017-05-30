package com.phdlabs.sungwon.heyoo.structure.aahome.eventedit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;
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
        if(mEvent==null){
            getBaseActivity().setToolbarTitle("New Event");
        } else {
            getBaseActivity().setToolbarTitle(mEvent.getName());
        }
        showEventOption();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void showEventOption(){
        MainActivity activity = (MainActivity)getActivity();
        activity.showBackArrow(android.R.drawable.ic_menu_close_clear_cancel);
        Toolbar toolbar = activity.getToolbar();
        toolbar.getMenu().clear();
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

