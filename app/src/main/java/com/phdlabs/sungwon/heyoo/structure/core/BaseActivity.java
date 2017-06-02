package com.phdlabs.sungwon.heyoo.structure.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;

/**
 * Created by SungWon on 4/13/2017.
 */

public abstract class BaseActivity extends CoreActivity {

    /**
     *
     */
    public final String TAG = getClass().getSimpleName();

    private Toolbar mToolbar;

    @LayoutRes
    protected abstract int layoutId();

    @IdRes
    protected abstract int contentContainerId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    public void addFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        super.addFragment(contentContainerId(), fragment, addToBackStack);
    }

    public void replaceFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        super.replaceFragment(contentContainerId(), fragment, addToBackStack);
    }

    public Context getContext() {
        return this;
    }

    public void showError(String errorMessage) {
        new AlertDialog.Builder(this).setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, null).show();
    }

    public void setToolbarTitle(@StringRes int title) {
        setToolbarTitle(getString(title));
    }

    public void setToolbarTitle(String title) {
        View view = findById(R.id.toolbar_title);
        if (view != null) {
            ((TextView) view).setText(title);
        }
    }

    public void showBackArrow(int icon){
        mToolbar.setNavigationIcon(icon);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                    mToolbar.setNavigationIcon(null);
                }
            }
        });
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }
//
//    public void showProgress() {
//        View progress = findById(R.id.progress_view);
//        if (progress != null) {
//            progress.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void hideProgress() {
//        View progress = findById(R.id.progress_view);
//        if (progress != null) {
//            progress.setVisibility(View.GONE);
//        }
//    }
}
