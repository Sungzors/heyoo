package com.phdlabs.sungwon.heyoo.Structure.Mainactivity;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.widget.Toolbar;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.Structure.Core.BaseActivity;

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

        mTabLayout = findById(R.id.tab_layout);
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case TAB_HOME:
                mController.onHomeSelected();
                break;
            case TAB_CALENDAR:
                mController.onCalendarSelected();
                break;
            case TAB_TASKS:
                mController.onTasksSelected();
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
