package com.phdlabs.sungwon.heyoo.structure.aahome;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendarManager;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.acevents.event.EventFragment;
import com.phdlabs.sungwon.heyoo.structure.acevents.eventdraft.EventDraftFragment;
import com.phdlabs.sungwon.heyoo.structure.acevents.eventedit.EventEditFragment;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.EventDecorator;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by SungWon on 4/18/2017.
 */

public class HomeFragment extends BaseFragment<HomeContract.Controller>
        implements HomeContract.View, View.OnClickListener, TabLayout.OnTabSelectedListener, DayViewDecorator{

    /**
     * Fragment for Home tab, includes calendar
     */

    private static final String TAG = "HomeFragment";

    private View mEmptyView;
    private TabLayout mTabLayout;
    private TextView mTestText;
    private ImageView mTabLeft;
    private MaterialCalendarView mCalendarView;
    private MaterialCalendarView.StateBuilder mStateBuilder;
    private Toolbar mToolbar;
    private Menu mMenu;
    private HashSet<CalendarDay> mDayHash;
    private HashSet mSet;
    private BaseListRecyclerAdapter<HeyooEvent, BaseViewHolder> mRecyclerAdapter;
    private HeyooEvent mLastHeyooEvent;
    private HeyooEvent mDummyHeyooEvent;
    private Calendar mToday;
    private RecyclerView mEventList;

    private HeyooCalendarManager mCalendarManager;

    private int mCalID = -1;

    @NonNull
    @Override
    protected HomeContract.Controller createController() {
        return new HomeController(this);
    }

    public static HomeFragment newInstance(){
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeFragment newInstance(int calID){
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putInt(Constants.BundleKeys.HOME_CALENDAR_ID, calID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).eraseBackArrow();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        mTestText = findById(R.id.test_text2);
//        mTestText.setText("hello");
        mCalID = getArguments().getInt(Constants.BundleKeys.HOME_CALENDAR_ID, -1);
        mCalendarManager = HeyooCalendarManager.getInstance(getContext());
        if(mCalID == -1){
            getBaseActivity().setToolbarTitle(R.string.home);
        } else {
            getBaseActivity().setToolbarTitle(mCalendarManager.getCalendar(mCalID).getName());
        }
        mTabLayout = ((MainActivity)getBaseActivity()).getTabLayout();
        if(mCalID == -1){
            mTabLayout.getTabAt(0).select();
        }
        mCalendarView = findById(R.id.material_calendar_view);
        mCalendarView.addDecorator(this);
        mCalendarView.addDecorator(decorateBackground(getDummyDates()));
        mStateBuilder = mCalendarView.newState();
        mToolbar = ((MainActivity)getActivity()).getToolbar();
        mMenu = mToolbar.getMenu();
        mMenu.clear();
        Date dummydate = new Date();
        dummydate.setTime(10000);
        mDummyHeyooEvent = new HeyooEvent(0, "dummy", dummydate, dummydate, "dummy event. If you see this, something has gone wrong", false, 9999, null);
        mLastHeyooEvent = mDummyHeyooEvent;
        mToday = Calendar.getInstance();
        mCalendarView.setDateSelected(mToday, true);
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                controller.onDaySelected();
                mLastHeyooEvent = mDummyHeyooEvent;
            }
        });
        showCalendarOption();
        showAddOption();
        setupRecyclerAdapter();
        mEventList = findById(R.id.event_list);
        mEventList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEventList.setAdapter(mRecyclerAdapter);



        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    public void showAddOption(){
//        TextView addbutton = findById(R.id.right_action);
//        addbutton.setBackgroundResource(R.drawable.bluekillerballstaresatyouwithitsblankeyes);
//        addbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "add clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void showCalendarOption(){
        mMenu.clear();
        mToolbar.inflateMenu(R.menu.menu_calendar);
        mMenu.setGroupCheckable(R.id.menu_group_calendar, true, true);
        mMenu.getItem(1).setChecked(true);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_calendar_btn:
                        showDraftFragment();
                        return true;
                    case R.id.action_full_cal:
                        showFullCalendar();
                        item.setChecked(!item.isChecked());
                        return true;
                    case R.id.action_partial_cal:
                        showPartialCalendar();
                        item.setChecked(!item.isChecked());
                        return true;
                    case R.id.action_new_event:
                        showAddEvent();
                        return true;
                }
                return false;
            }
        });
    }

    private void setupRecyclerAdapter(){
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
                                showEventDetail(event);
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

    @Override
    public void onResume() {
        super.onResume();
        mLastHeyooEvent = mDummyHeyooEvent;
    }

    @Override
    public void onPause() {
        super.onPause();
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
        if(heyooEvent.getCalendars() == 1){
            changeTabColor();
        }
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.red_circle_selector,null));
        DayFormatter formatter = new DayFormatter() {
            @NonNull
            @Override
            public String format(@NonNull CalendarDay day) {
                return null;
            }
        };
    }

    /**
     * month based on Calendar, meaning it starts from 0
     * @return
     */
    public HashSet<CalendarDay> getDummyDates(){
        HashSet<CalendarDay> dates = new HashSet<>();
        dates.add(CalendarDay.from(2017, 6, 1));
        dates.add(CalendarDay.from(2017, 6, 4));
        dates.add(CalendarDay.from(2017, 6, 5));
        return dates;
    }

    public EventDecorator decorateBackground(HashSet<CalendarDay> hashSet){
        return new EventDecorator(ResourcesCompat.getDrawable(getResources(),R.drawable.calendar_backlight_selector,null), hashSet){
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return super.shouldDecorate(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                super.decorate(view);
            }
        };

    }

    @Override
    public void refreshList(){
        mRecyclerAdapter.notifyDataSetChanged();
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

    public void changeTabColor(/*Calendar calendar*/){//TODO: implement dynamically changing color based on heyoocalendar's color id
        GradientDrawable bgshape = (GradientDrawable)mTabLeft.getBackground();
        bgshape.setColor(Color.BLUE);
    }


    @Override
    public void showFullCalendar() {
//        mTestText.setText("Full Calendar mode");
        mStateBuilder.setCalendarDisplayMode(CalendarMode.MONTHS);
        mStateBuilder.commit();
    }

    @Override
    public void showPartialCalendar() {
//        mTestText.setText("Partial Calendar mode");
        mStateBuilder.setCalendarDisplayMode(CalendarMode.WEEKS);
        mStateBuilder.commit();
    }

    @Override
    public int getSelectedTab() {
//        mTestText.setText("Selected Tab number: "+String.valueOf(mTabLayout.getSelectedTabPosition()));
        return mTabLayout.getSelectedTabPosition();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    @Override
    public void showEvents(List<HeyooEvent> heyooEvents) {
        List<HeyooEvent> heyooFiltered = new ArrayList<>();
        for (int i = 0; i < heyooEvents.size(); i++) {
            if(CalendarDay.from(heyooEvents.get(i).getStart_time()).isAfter(mCalendarView.getSelectedDate())){
                heyooFiltered.add(heyooEvents.get(i));
            } else if (CalendarDay.from(heyooEvents.get(i).getStart_time()).equals(mCalendarView.getSelectedDate())){
                heyooFiltered.add(heyooEvents.get(i));
            }
        }
        mRecyclerAdapter.setItems(heyooFiltered);
        mSet = new HashSet();
        for (int i = 0; i < heyooEvents.size(); i++) {
            mSet.add(CalendarDay.from(heyooEvents.get(i).getStart_time()));
        }
        mCalendarView.addDecorator(decorateBackground(mSet));
    }

    @Override
    public void showAddEvent() {
//        EventEditActivity.start(getContext(), null);
        getBaseActivity().replaceFragment(EventEditFragment.newInstance(null),true);
    }

    @Override
    public void showDraftFragment() {
        getBaseActivity().replaceFragment(EventDraftFragment.newInstance(), true);
    }

    @Override
    public void showEventDetail(HeyooEvent event) {
        getBaseActivity().replaceFragment(EventFragment.newInstance(event), true);
    }


}
