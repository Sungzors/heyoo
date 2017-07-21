package com.phdlabs.sungwon.heyoo.structure.aealerts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.event.AlertRetrievalEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooAlert;
import com.phdlabs.sungwon.heyoo.model.HeyooAlertManager;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by SungWon on 7/20/2017.
 */

public class AlertFragment extends BaseFragment<AlertContract.Controller>
        implements AlertContract.View{

    private RecyclerView mRecycler;
    private BaseListRecyclerAdapter<HeyooAlert, BaseViewHolder> mAdapter;

    private List<HeyooAlert> mAlerts;
    private HeyooAlertManager mAlertManager;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = findById(R.id.fa_alert_list);
        mAlertManager = HeyooAlertManager.getInstance();
        mAlertManager.getmEvents().register(this);
        showProgress();
        mAlertManager.loadAlerts();
    }

    @Subscribe
    public void onEvent(AlertRetrievalEvent event){
        if (event.isSuccess()){
            hideProgress();
            mAlerts = mAlertManager.getAlerts();
            showList();
            mAlertManager.getmEvents().unregister(this);
        } else {
            showError(event.getErrorMessage());
        }
    }

    private void showList() {
        mAdapter = new BaseListRecyclerAdapter<HeyooAlert, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooAlert data, int position, int type) {
                bindItemViewHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_alert, inflater, parent);
            }
        };
        mAdapter.setItems(mAlerts);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mAdapter);
    }

    private void bindItemViewHolder(BaseViewHolder viewHolder, HeyooAlert data) {
        Picasso.with(getContext()).load(data.getSender().getProfile_picture()).placeholder(R.drawable.pandapic).into((ImageView)viewHolder.get(R.id.cva_avatar));
        if(TextUtils.isEmpty(data.getTitle())){
            ((TextView)viewHolder.get(R.id.cva_title)).setText(data.getTitle());
        }
        if(TextUtils.isEmpty(data.getBody())){
            ((TextView)viewHolder.get(R.id.cva_message)).setText(data.getBody());
        }
    }
}
