package com.phdlabs.sungwon.heyoo.structure.aahome;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;
import com.phdlabs.sungwon.heyoo.utility.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.HashSet;

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
    private MaterialCalendarView mCalendarView;
    private MaterialCalendarView.StateBuilder mStateBuilder;
    private Toolbar mToolbar;
    private Menu mMenu;
    private HashSet<CalendarDay> mDayHash;

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
        mTestText = findById(R.id.test_text2);
        mTestText.setText("hello");
        mTabLayout = findById(R.id.tab_layout);
        mCalendarView = findById(R.id.material_calendar_view);
        mCalendarView.addDecorator(this);
        mCalendarView.addDecorator(decorateBackground(getDummyDates()));
        mStateBuilder = mCalendarView.newState();
        mToolbar = ((MainActivity)getActivity()).getToolbar();
        mMenu = mToolbar.getMenu();
        showCalendarOption();
        showAddOption();
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
        mMenu.getItem(0).setChecked(true);
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


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        mCalendarView.setDateSelected(Calendar.getInstance(),true);
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.red_circle_selector,null));
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
        return new EventDecorator(ResourcesCompat.getDrawable(getResources(),R.drawable.calendar_backlight,null), hashSet);
    }

    @Override
    public void showFullCalendar() {
        mTestText.setText("Full Calendar mode");
        mStateBuilder.setCalendarDisplayMode(CalendarMode.MONTHS);
        mStateBuilder.commit();
    }

    @Override
    public void showPartialCalendar() {
        mTestText.setText("Partial Calendar mode");
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
    public void showAddEvent() {

    }

    @Override
    public void showEventDetail() {

    }


}
