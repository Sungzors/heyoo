package com.phdlabs.sungwon.heyoo.structure.abcalender.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendarManager;
import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;

/**
 * Created by SungWon on 6/12/2017.
 */

public class CalendarFragment extends BaseFragment<CalendarContract.Controller>
        implements CalendarContract.View{

    private static final String TAG = "CalendarFragment";

    private ImageView mCalendarIcon;
    private TextView mCalendarTitle;
    private TextView mCalendarAdd;
    private ImageView mTabLeft;
    private TextView mCardEventTitle;
    private ImageView mCardAlertIcon;
    private TextView mCardAlertNum;
    private ImageView mCardPeopleIcon;
    private TextView mCardPeopleNum;

    private RecyclerView mRecycler;
    private BaseListRecyclerAdapter<HeyooCalendar, BaseViewHolder> mAdapter;

    private HeyooCalendarManager mCalendarManager;

    @NonNull
    @Override
    protected CalendarContract.Controller createController() {
        return new CalendarController(this);
    }

    public static CalendarFragment newInstance(){
        Bundle args = new Bundle();
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_calendar;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarIcon = findById(R.id.dcco_cal_icon_back);
        mCalendarTitle = findById(R.id.fc_calendar_title);
        mCalendarAdd = findById(R.id.fc_calendar_add);
        mRecycler = findById(R.id.fc_calendar_list);
    }

    private void setUpRecycler(){
        mAdapter = new BaseListRecyclerAdapter<HeyooCalendar, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooCalendar data, int position, int type) {
                bindItemViewHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_calendar, inflater, parent){
                    @Override
                    protected void addClicks(ViewMap views) {
                        views.click(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                HeyooCalendar calendar = getItem(getAdapterPosition());
                                showCalendarDetails(calendar);
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
    }

    private void bindItemViewHolder(BaseViewHolder viewHolder, HeyooCalendar calendar){

    }

    @Override
    public void showCalendarDetails(HeyooCalendar calendar) {

    }
}
