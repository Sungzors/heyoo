package com.phdlabs.sungwon.heyoo.structure.acevents.eventedit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;
import com.phdlabs.sungwon.heyoo.structure.core.BaseActivity;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.adapter.EventEditRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/30/2017.
 */

public class EventEditFragment extends BaseFragment<EventEditContract.Controller>
        implements EventEditContract.View, View.OnClickListener{


    private HeyooEvent mEvent;
    private HeyooEventManager mEventManager;
    private Button mPublishButton;
    private Button mDraftButton;

    private RecyclerView mRecyclerView;
    private EventEditRecyclerAdapter mAdapter;

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
        mEventManager = HeyooEventManager.getInstance();
        showEventOption();
        showEventEdit();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPublishButton = findById(R.id.edit_publish_button);
        mDraftButton = findById(R.id.edit_draft_button);
        mPublishButton.setOnClickListener(this);
        mDraftButton.setOnClickListener(this);
    }

    public void showEventOption(){
        BaseActivity activity = (BaseActivity)getActivity();
        activity.showBackArrow(android.R.drawable.ic_menu_close_clear_cancel);
        Toolbar toolbar = activity.getToolbar();
        toolbar.getMenu().clear();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void showEventEdit() {
        mRecyclerView = findById(R.id.event_edit_detail_list);
        List<HeyooEvent> repeatList = new ArrayList<>();
        repeatList.add(mEvent);
        repeatList.add(mEvent);
        repeatList.add(mEvent);
        repeatList.add(mEvent);
        repeatList.add(mEvent);
        mAdapter = new EventEditRecyclerAdapter(repeatList, controller);
        mAdapter.setItems(repeatList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void saveEvent() {
        HeyooEvent event = mAdapter.getEvent();
        event.setPublished(false);
        if(event.getId()==-1){
            event.setId(mEventManager.getMapEvents().size());
        }
        mEventManager.addEvents(event);
        //TODO: post to server
//        Intent intent = new Intent(getContext(), HomeActivity.class);
//        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);
        getBaseActivity().onBackPressed();
    }

    @Override
    public void postEvent() {
        HeyooEvent event = mAdapter.getEvent();
        if(!event.isPublished()){
            event.setPublished(true);
        }
        if(event.getId()==-1){
            event.setId(mEventManager.getMapEvents().size());
        }
        mEventManager.addEvents(event);
        //TODO: post to server. For now, ID will be place in map
//        Intent intent = new Intent(getContext(), HomeActivity.class);
//        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);
        getBaseActivity().onBackPressed();
    }

//    @Override
//    public boolean onBackPressed() {
//        getBaseActivity().finish();
//        return true;
//    }

    @Override
    public void hideProgress() {

    }

    @Override
    public int getEventid() {
        if(mEvent == null){
            return -1;
        }
        return mEvent.getId();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_publish_button:
                controller.onPublishClicked();
                break;
            case R.id.edit_draft_button:
                controller.onSaveDraftClicked();
                break;
        }
    }
}

