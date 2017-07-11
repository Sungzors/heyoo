package com.phdlabs.sungwon.heyoo.structure.abcalender.calendar;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.event.CalendarRetrievalEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendarManager;
import com.phdlabs.sungwon.heyoo.structure.aahome.HomeFragment;
import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;
import com.phdlabs.sungwon.heyoo.structure.abcalender.add.CalendarAddFragment;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

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

    private List<HeyooCalendar> mCalendars;

    private Toolbar mToolbar;
    private Menu mMenu;

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
        getBaseActivity().setToolbarTitle(R.string.my_calendars);
        ((MainActivity)getActivity()).eraseBackArrow();
        mCalendarManager.getEventBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mCalendarManager.getEventBus().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarIcon = findById(R.id.dcco_cal_icon_back);
        mCalendarTitle = findById(R.id.fc_calendar_title);
        mCalendarAdd = findById(R.id.fc_calendar_add);
        mRecycler = findById(R.id.fc_calendar_list);
        mToolbar = ((MainActivity)getActivity()).getToolbar();
        mMenu = mToolbar.getMenu();
        mMenu.clear();
//        mToolbar.inflateMenu(R.menu.menu_edit);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return onOptionsItemSelected(item);
//            }
//        });

        mCalendarManager = HeyooCalendarManager.getInstance(new Preferences(getContext()).getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null));
        mCalendarManager.loadCalendars();
        mCalendarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseActivity().replaceFragment(CalendarAddFragment.newInstance(), true);
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_edit:
//                controller.onEdit();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Subscribe
    public void onEventMainThread(CalendarRetrievalEvent event){
        if (event.isSuccess()){
            mCalendars = mCalendarManager.getCalendars();
            setUpRecycler();
            mCalendarTitle.setText("Calendars (" + mCalendars.size() + ")");
        } else {
            showError(event.getErrorMessage());
        }
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
                };
            }
        };
        mAdapter.setItems(mCalendars);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mAdapter);
    }

    private void bindItemViewHolder(BaseViewHolder viewHolder, HeyooCalendar calendar){
        mTabLeft = viewHolder.get(R.id.cvc_tab_left);
        mCardEventTitle = viewHolder.get(R.id.cvc_calendar_title);
        mCardAlertIcon = viewHolder.get(R.id.dcao_icon_background);
        mCardAlertNum = viewHolder.get(R.id.cvc_calendar_alert_number);
        mCardPeopleIcon = viewHolder.get(R.id.dcpd_people_icon_bg);
        mCardPeopleNum = viewHolder.get(R.id.cvc_calendar_people_number);
        String status;
        if (calendar.getName().equals("Master")){
            status = " (Main)";
        } else if (calendar.getOwner_id()==7 /*TODO: insert owner id from gotten user*/){
            status = " (Owner)";
        } else {
            status = " (Shared)";
        }
        mCardEventTitle.setText(calendar.getName()+status);

        GradientDrawable bgshape = (GradientDrawable)mTabLeft.getBackground();
        bgshape.setColor(Constants.getColor(calendar.getColor()));
        GradientDrawable alertshape = (GradientDrawable)mCardAlertIcon.getBackground();
        alertshape.setColor(Constants.getColor(calendar.getColor()));
        mCardAlertNum.setText(String.valueOf(2));
        GradientDrawable peopleshape = (GradientDrawable)mCardPeopleIcon.getBackground();
        peopleshape.setColor(Constants.getColor(calendar.getColor()));
        mCardPeopleNum.setText(String.valueOf(3));//TODO: set number value according to size of list of users
    }

    @Override
    public void showCalendarDetails(HeyooCalendar calendar) {
        getBaseActivity().replaceFragment(HomeFragment.newInstance(calendar.getId()),true);
    }
}
