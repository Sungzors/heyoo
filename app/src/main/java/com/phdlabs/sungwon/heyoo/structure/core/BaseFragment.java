package com.phdlabs.sungwon.heyoo.structure.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.phdlabs.sungwon.heyoo.BuildConfig;
import com.phdlabs.sungwon.heyoo.R;

/**
 * Created by SungWon on 4/13/2017.
 */

public abstract class BaseFragment<Controller extends Contract.BaseController> extends CoreFragment {
    protected Controller controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = createController();
    }

    @NonNull
    protected abstract Controller createController();

    @Override
    public void onStart() {
        super.onStart();
        controller.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.onResume();
    }

    @Override
    public void onPause() {
        controller.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        controller.onStop();
        super.onStop();
    }


    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        } else {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("Parent activity doesn't extend BaseActivity");
            } else
                return null;
        }
    }


    public void showError(String message) {
        new AlertDialog.Builder(getActivity()).setMessage(message)
                .setPositiveButton(android.R.string.ok, null).show();
    }

    public void showProgress() {
        View progress = findById(R.id.progress_view);
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        View progress = findById(R.id.progress_view);
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }
    }

    public void close() {
        getBaseActivity().closeFromChild();
    }
}
