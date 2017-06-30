package com.phdlabs.sungwon.heyoo.structure.aahome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.image.ImageFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends MainActivity<HomeContract.Activity.Controller>
        implements HomeContract.Activity.View{


    private HomeFragment mHomeFragment;


    @NonNull
    @Override
    protected HomeContract.Activity.Controller createController() {
        return new HomeActivityController(this);
    }

    @Override
    protected int tabIndex() {
        return TAB_HOME;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, ImageFragment.newInstance(null)).commitAllowingStateLoss();
//        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, getHomeFragment()).commitAllowingStateLoss();
    }

    private Fragment getHomeFragment(){
        Fragment fragment;
        if (mHomeFragment == null){
            mHomeFragment = HomeFragment.newInstance();
        }
        fragment = mHomeFragment;
        return fragment;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
