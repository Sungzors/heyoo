package com.phdlabs.sungwon.heyoo.structure.mainactivity;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class MainActivity<Controller extends MainContract.Controller> extends BaseActivity
        implements MainContract.View, TabLayout.OnTabSelectedListener{

    /**
     * MainActivity is an abstract class with the purpose of setting up the rules for a bottom TabLayout in the main view of the app.
     * Needs to be extended by any Activity relating to a tab, requires a Controller corresponding to Activity
     */
    public static final int TAB_HOME = 0;
    public static final int TAB_CALENDAR = 1;
    public static final int TAB_TASKS = 2;
    public static final int TAB_MESSAGES = 3;
    public static final int TAB_ALERTS = 4;
    private Toolbar mToolbar;

    /**
     * Limits selectable tab to the 5 listed in IntDef
     */
    @IntDef({TAB_HOME, TAB_CALENDAR, TAB_TASKS, TAB_MESSAGES, TAB_ALERTS})
    @Retention(RetentionPolicy.SOURCE)
    @interface Tab {

    }

    private Controller mController;

    private TabLayout mTabLayout;
    private TextView mTestText;

    /**
     * view set up
     * @return layout id
     */
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int contentContainerId() {
        return R.id.content_frame;
    }

    @NonNull
    protected abstract Controller createController();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = createController();
        mTestText = (TextView)findViewById(R.id.test_text);
        mTabLayout = findById(R.id.tab_layout);
        setupTabs(mTabLayout);
        mTabLayout.addOnTabSelectedListener(this);

    }

    private void setupTabs(TabLayout mTabLayout) {
        TabLayout.Tab home = mTabLayout.newTab().setText(R.string.home).setIcon(R.drawable.tab_home_icon_setup);
        TabLayout.Tab calendar = mTabLayout.newTab().setText(R.string.calendar).setIcon(R.drawable.tab_calendar_icon_setup);
        TabLayout.Tab tasks = mTabLayout.newTab().setText(R.string.task).setIcon(R.drawable.tab_task_icon_setup);
        TabLayout.Tab messages = mTabLayout.newTab().setText(R.string.message).setIcon(R.drawable.tab_message_icon_setup);
        TabLayout.Tab alerts = mTabLayout.newTab().setText(R.string.alerts).setIcon(R.drawable.tab_alert_icon_setup);
        mTabLayout.setTabTextColors(R.color.tabnotselected,R.color.tabselected);
        mTabLayout.addTab(home);
        mTabLayout.addTab(calendar);
        mTabLayout.addTab(tasks);
        mTabLayout.addTab(messages);
        mTabLayout.addTab(alerts);
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        tab.select();
    }

    @Tab
    protected abstract int tabIndex();

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case TAB_HOME:
                mController.onHomeSelected();
                mTestText.setText("Home selected");
                break;
            case TAB_CALENDAR:
                mController.onCalendarSelected();
                mTestText.setText("Calendar selecto");
                break;
            case TAB_TASKS:
                mController.onTasksSelected();
                mTestText.setText("Tasks");
                break;
            case TAB_MESSAGES:
                mController.onMessagesSelected();
                break;
            case TAB_ALERTS:
                mController.onAlertsSelected();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void showHomePage() {

    }

    @Override
    public void showCalendarPage() {

    }

    @Override
    public void showTasksPage() {

    }

    @Override
    public void showMessagesPage() {

    }

    @Override
    public void showAlertsPage() {

    }



    @Override
    public void close() {
        finishAffinity();
    }
}
