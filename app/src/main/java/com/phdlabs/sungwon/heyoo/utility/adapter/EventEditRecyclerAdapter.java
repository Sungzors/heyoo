package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.aahome.eventedit.EventEditContract;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;

import java.util.List;

/**
 * Created by SungWon on 5/30/2017.
 */

public class EventEditRecyclerAdapter extends BaseListRecyclerAdapter<HeyooEvent, BaseViewHolder>
        implements View.OnClickListener{

    EventEditContract.Controller mController;

    public EventEditRecyclerAdapter(@NonNull List<HeyooEvent> values, EventEditContract.Controller mController) {
        super(values);
        this.mController = mController;
    }

    @Override
    protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooEvent data, int position, int type) {

    }

    @Override
    protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
        return null;
    }

    @Override
    public void onClick(View view) {

    }
}
