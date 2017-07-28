package com.phdlabs.sungwon.heyoo.structure.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseActivity;

/**
 * Created by SungWon on 7/27/2017.
 */

public class ProfileActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected int layoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected int contentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = findById(R.id.toolbar);
        mToolbar.setTitle("Create Your Profile");
        showBackArrow(R.drawable.ic_back);
        showProfileFragment();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void showBackArrow(int icon){
        mToolbar.setNavigationIcon(icon);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                    mToolbar.setNavigationIcon(null);
                }
            }
        });
    }

    private void showProfileFragment(){
        replaceFragment(ProfileFragment.newInstance(), true);
    }

    public static void start(Context context){
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }
}
