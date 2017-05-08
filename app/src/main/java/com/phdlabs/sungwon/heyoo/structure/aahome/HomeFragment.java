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
import android.widget.Toast;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;
import com.phdlabs.sungwon.heyoo.utility.BaseListRecyclerAdapter;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.EventDecorator;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.text.SimpleDateFormat;
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
    private BaseListRecyclerAdapter<HeyooEvent, BaseViewHolder> mRecyclerAdapter;
    private HeyooEvent mLastHeyooEvent;
    private HeyooEvent mDummyHeyooEvent;
    private Calendar mToday;
    private RecyclerView mEventList;

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

    @Override
    public void onStart() {
        super.onStart();
        getBaseActivity().setToolbarTitle(R.string.home);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        mTestText = findById(R.id.test_text2);
//        mTestText.setText("hello");
        mTabLayout = findById(R.id.tab_layout);
        mCalendarView = findById(R.id.material_calendar_view);
        mCalendarView.addDecorator(this);
        mCalendarView.addDecorator(decorateBackground(getDummyDates()));
        mStateBuilder = mCalendarView.newState();
        mToolbar = ((MainActivity)getActivity()).getToolbar();
        mMenu = mToolbar.getMenu();
        Date dummydate = new Date();
        dummydate.setTime(10000);
        mDummyHeyooEvent = new HeyooEvent(0, "dummy", dummydate, dummydate, false, 9999);
        mLastHeyooEvent = mDummyHeyooEvent;
        mToday = Calendar.getInstance();
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
                        Toast.makeText(getContext(), "What is this supposed to do?", Toast.LENGTH_SHORT).show();
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

    public void setupRecyclerAdapter(){
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
                        super.addClicks(views);
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
    public void onPause() {
        super.onPause();
        mLastHeyooEvent = mDummyHeyooEvent;
    }

    private void bindItemViewHolder(BaseViewHolder viewHolder, HeyooEvent heyooEvent){
        mTabLeft = viewHolder.get(R.id.cvh_tab_left);
        if(heyooEvent.getStartTimeHash() == mLastHeyooEvent.getStartTimeHash()){
            ((TextView)viewHolder.get(R.id.cvh_top_date_text)).setVisibility(TextView.GONE);
        } else if (heyooEvent.getStartTimeHash()== HeyooEvent.hashCode(mToday)){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
            ((TextView)viewHolder.get(R.id.cvh_top_date_text)).setText("Today - " + formatter.format(heyooEvent.getStartCalendar().getTime()));
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE - MMM dd");
            ((TextView)viewHolder.get(R.id.cvh_top_date_text)).setText(formatter.format(heyooEvent.getStartCalendar().getTime()));
        }
        mLastHeyooEvent = heyooEvent;
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm");
        ((TextView)viewHolder.get(R.id.cvh_time)).setText(formatter.format(heyooEvent.getStartCalendar().getTime()) + getAMPM(heyooEvent.getStartCalendar()));
        ((TextView)viewHolder.get(R.id.cvh_event_title)).setText(heyooEvent.getName());
        if(heyooEvent.getCalendar_id() == 1){
            changeTabColor();
        }
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        mCalendarView.setDateSelected(Calendar.getInstance(),true);
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
        mRecyclerAdapter.setItems(heyooEvents);
        HashSet set = new HashSet();
        for (int i = 0; i < heyooEvents.size(); i++) {
            set.add(CalendarDay.from(heyooEvents.get(i).getStart_time()));
        }
        mCalendarView.addDecorator(decorateBackground(set));
    }

    @Override
    public void showAddEvent() {

    }

    @Override
    public void showEventDetail() {

    }


}
