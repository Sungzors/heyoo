package com.phdlabs.sungwon.heyoo.structure.aahome;

/**
 * Created by SungWon on 4/18/2017.
 */

public class HomeController implements HomeContract.Controller{

    private HomeContract.View view;

    HomeController(HomeContract.View view){
        this.view = view;
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

    @Override
    public void onDaySelected() {

    }

    @Override
    public void onDayUnselected() {

    }
}
