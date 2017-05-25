package com.phdlabs.sungwon.heyoo.structure.aahome.event;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.adapter.EventRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/22/2017.
 */

public class EventFragment extends BaseFragment<EventContract.Controller>
        implements EventContract.View{

    private Menu mOptionsMenu;

    private HeyooEvent mEvent;
    private EventRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static EventFragment newInstance(HeyooEvent event){
        Bundle args = new Bundle();
        args.putSerializable(Constants.BundleKeys.EVENT_DETAIL, event);
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
        mEvent = (HeyooEvent) args.getSerializable(Constants.BundleKeys.EVENT_DETAIL);
        getBaseActivity().setToolbarTitle(mEvent.getName());
        showEventDetails();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mOptionsMenu = menu;
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                controller.onEdit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEventDetails() {
        mRecyclerView = findById(R.id.event_detail_list);
        List<HeyooEvent> repeatList = new ArrayList<>();
        repeatList.add(mEvent);
        repeatList.add(mEvent);
        repeatList.add(mEvent);
        repeatList.add(mEvent);
        repeatList.add(mEvent);
        mAdapter = new EventRecyclerAdapter(repeatList, controller);
        mAdapter.setItems(repeatList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }



    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public int getEventid() {
        return mEvent.getId();
    }

    @Override
    public void onStop() {
        mAdapter.clear();
        super.onStop();
    }
}
