package com.phdlabs.sungwon.heyoo.structure.abcalender.member;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendarManager;
import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.invite.InviteFragment;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by SungWon on 7/13/2017.
 */

public class CalendarMemberFragment extends BaseFragment<CalendarContract.Member.Controller>
        implements CalendarContract.Member.View, View.OnClickListener{

    private int mCalID = -1;
    private HeyooCalendarManager mCalendarManager;
    private HeyooCalendar mCalendar;

    private Button mAddButton;
    private RecyclerView mRecycler;
    private BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder> mAdapter;

    public static CalendarMemberFragment newInstance(int calID){
        Bundle args = new Bundle();
        args.putInt(Constants.BundleKeys.HOME_CALENDAR_ID, calID);
        CalendarMemberFragment fragment = new CalendarMemberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected CalendarContract.Member.Controller createController() {
        return new CalendarMemberController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_calendar_member;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalID = getArguments().getInt(Constants.BundleKeys.HOME_CALENDAR_ID, -1);
        mCalendarManager = HeyooCalendarManager.getInstance();
        mCalendar = mCalendarManager.getCalendar(mCalID);

        mAddButton = findById(R.id.fcm_calendar_add);
        mAddButton.setOnClickListener(this);
        mRecycler = findById(R.id.fcm_calendar_member_list);
    }

    private void setUpRecycler(){
        mAdapter = new BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooAttendee data, int position, int type) {
                bindItemViewHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_calendar_member, inflater, parent){
                    @Override
                    protected void addClicks(ViewMap views) {
                        onMemberClicked(getItem(getAdapterPosition()));
                    }
                };
            }
        };
        mAdapter.setItems(mCalendar.getUsers());
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mAdapter);
    }

    private void bindItemViewHolder(BaseViewHolder viewHolder, HeyooAttendee attendee){
        ((TextView)viewHolder.get(R.id.cvcm_member_name)).setText(attendee.getFirst_name() + " " + attendee.getLast_name());
        Picasso.with(getContext()).load(attendee.getProfile_picture()).placeholder(R.drawable.pandapic).into((ImageView)viewHolder.get(R.id.cvcm_member_avatar));
    }

    @Override
    public void onMemberClicked(HeyooAttendee attendee) {

    }

    @Override
    public void onAddClicked() {
        getBaseActivity().replaceFragment(InviteFragment.newInstance(mCalID), true);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fcm_calendar_add:
                onAddClicked();
                break;
        }
    }


}
