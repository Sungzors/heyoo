package com.phdlabs.sungwon.heyoo.structure.aealerts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 7/20/2017.
 */

public class AlertFragment extends BaseFragment<AlertContract.Controller>
        implements AlertContract.View{

    public static AlertFragment newInstance(){
        Bundle args = new Bundle();
        AlertFragment fragment = new AlertFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected AlertContract.Controller createController() {
        return new AlertController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_alert;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
