package com.phdlabs.sungwon.heyoo.structure.acevents.eventdraft;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;
import com.phdlabs.sungwon.heyoo.structure.acevents.event.EventContract;
import com.phdlabs.sungwon.heyoo.structure.acevents.eventedit.EventEditFragment;
import com.phdlabs.sungwon.heyoo.structure.aahome.home.HomeFragment;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SungWon on 6/5/2017.
 */

public class EventDraftFragment extends BaseFragment<EventContract.Draft.Controller>
        implements EventContract.Draft.View, View.OnClickListener{

    private static final String TAG = "EventDraftFragment";

    private ImageView mTabLeft;
    private Button mPublishAllButton;
    private Calendar mToday;
    private HeyooEvent mDummyHeyooEvent;
    private HeyooEvent mLastHeyooEvent;
    private RecyclerView mEventRecycler;
    private BaseListRecyclerAdapter<HeyooEvent, BaseViewHolder> mRecyclerAdapter;
    private HeyooEventManager mEventManager;
    private List<HeyooEvent> mEventList;

    @Override
    protected int layoutId() {
        return R.layout.fragment_draft_event;
    }

    @NonNull
    @Override
    protected EventContract.Draft.Controller createController() {
        return new EventDraftController(this);
    }

    public static EventDraftFragment newInstance(){
        Bundle args = new Bundle();
        EventDraftFragment fragment = new EventDraftFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() started");
        getBaseActivity().setToolbarTitle("Unpublished Events ("+ mEventList.size() +")");
        Log.d(TAG, "onStart() ended");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated started");
        Date dummydate = new Date();
        dummydate.setTime(10000);
        mDummyHeyooEvent = new HeyooEvent(0, "dummy", dummydate, dummydate, "dummy event. If you see this, something has gone wrong", false, 9999, null);
        mLastHeyooEvent = mDummyHeyooEvent;
        mEventManager = HeyooEventManager.getInstance();
        mEventList = mEventManager.getUnpublishedEvents();
        mToday = Calendar.getInstance();
        mPublishAllButton = findById(R.id.draft_publish_all_button);
        mPublishAllButton.setOnClickListener(this);
        Toolbar toolbar = ((MainActivity)getActivity()).getToolbar();
        Menu menu = toolbar.getMenu();
        menu.clear();
        showDrafts();
        showMenu();
        Log.d(TAG, "onCreateView ended");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showDrafts() {
        mEventRecycler = findById(R.id.event_list);
        mRecyclerAdapter = new BaseListRecyclerAdapter<HeyooEvent, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooEvent data, int position, int type) {
                bindItemViewHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_home, inflater, parent){
                    @Override
                    protected void addClicks(ViewMap views) {
                        views.click(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                HeyooEvent event = getItem(getAdapterPosition());
                                showSelectedDraft(event);
                            }
                        });
                    }

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvh_top_date_text, R.id.cvh_time, R.id.cvh_event_title);
                    }
                };
            }
        };
        mRecyclerAdapter.setItems(mEventList);
        mEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEventRecycler.setAdapter(mRecyclerAdapter);
    }

    private void bindItemViewHolder(BaseViewHolder viewHolder, HeyooEvent heyooEvent){
        mTabLeft = viewHolder.get(R.id.cvh_tab_left);
        if(heyooEvent.getStartTimeHash() == mLastHeyooEvent.getStartTimeHash()){
            (viewHolder.get(R.id.cvh_top_date_text)).setVisibility(TextView.GONE);
        } else if (heyooEvent.getStartTimeHash()== HeyooEvent.hashCode(mToday)){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
            ((TextView)viewHolder.get(R.id.cvh_top_date_text)).setText("Today - " + formatter.format(heyooEvent.getStartCalendar().getTime()));
            (viewHolder.get(R.id.cvh_top_date_text)).setVisibility(TextView.VISIBLE);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE - MMM dd");
            ((TextView)viewHolder.get(R.id.cvh_top_date_text)).setText(formatter.format(heyooEvent.getStartCalendar().getTime()));
            (viewHolder.get(R.id.cvh_top_date_text)).setVisibility(TextView.VISIBLE);
        }
        mLastHeyooEvent = heyooEvent;
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm");
        ((TextView)viewHolder.get(R.id.cvh_time)).setText(formatter.format(heyooEvent.getStartCalendar().getTime()) + getAMPM(heyooEvent.getStartCalendar()));
        ((TextView)viewHolder.get(R.id.cvh_event_title)).setText(heyooEvent.getName());
    }

    public void showMenu(){
        MainActivity activity = (MainActivity)getActivity();
        activity.showBackArrow(android.R.drawable.ic_menu_close_clear_cancel);
        Toolbar toolbar = activity.getToolbar();
        toolbar.getMenu().clear();
    }

    public String getAMPM(Calendar calendar){
        String ampm = "a";
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if(hourOfDay>12) {
            ampm = "p";
        } else if(hourOfDay==12) {
            ampm = "p";
        }
        return ampm;
    }

    @Override
    public void showSelectedDraft(HeyooEvent event) {
//        EventEditActivity.start(getContext(), event);
        getBaseActivity().replaceFragment(EventEditFragment.newInstance(event), true);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.draft_publish_all_button:
                for (int i = 0; i < mEventList.size(); i++) {
                    HeyooEvent event = mEventList.get(i);
                    event.setPublished(true);
                    mEventManager.setEvents(event.getId(), event);
                    getBaseActivity().replaceFragment(HomeFragment.newInstance(), false);
                }
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


}
