package com.phdlabs.sungwon.heyoo.Structure.Mainactivity;

import android.content.Context;

/**
 * Created by SungWon on 4/13/2017.
 */

public abstract class MainController<View extends MainContract.View> implements MainContract.Controller{

    private View view;

    public MainController(View view) {
        this.view = view;
    }

    @Override
    public void onCalendarSelected() {
        view.showCalendarPage();
    }

    @Override
    public void onTasksSelected() {
        view.showTasksPage();
    }

    @Override
    public void onMessagesSelected() {
        view.showMessagesPage();
    }

    @Override
    public void onAlertsSelected() {
        view.showAlertsPage();
    }

    @Override
    public void onHomeSelected() {
        view.showHomePage();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    public static void openApp(Context context) {

    }
}
