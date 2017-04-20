package com.phdlabs.sungwon.heyoo.structure.aahome;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 4/18/2017.
 */

public class HomeFragment extends BaseFragment<HomeContract.Controller>
        implements HomeContract.View, View.OnClickListener, TabLayout.OnTabSelectedListener{

    private static final String TAG = "HomeFragment";

    private View mEmptyView;
    private TabLayout mTabLayout;
    private TextView mTestText;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        mTestText = (TextView)view.findViewById(R.id.test_text);
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

    @Override
    public void showFullCalendar() {

    }

}
