package com.phdlabs.sungwon.heyoo.structure.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

    @LayoutRes
    protected abstract int layoutId();

    @IdRes
    protected abstract int contentContainerId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
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
