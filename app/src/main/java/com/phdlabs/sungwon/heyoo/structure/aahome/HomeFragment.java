package com.phdlabs.sungwon.heyoo.structure.aahome;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

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
//        mTestText = (TextView)view.findViewById(R.id.test_text);
        mTabLayout = findById(R.id.tab_layout);
        mCalendarView = findById(R.id.material_calendar_view);
        mCalendarView.addDecorator(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void showPartialCalendar() {

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.red_circle,null));
    }

    @Override
    public void showFullCalendar() {

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
