package com.phdlabs.sungwon.heyoo.structure.aahome.home;

import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainController;

/**
 * Created by SungWon on 4/18/2017.
 */

public class HomeActivityController extends MainController<HomeContract.Activity.View>
        implements HomeContract.Activity.Controller{

    public HomeActivityController(HomeContract.Activity.View view){
        super(view);
    }
}
