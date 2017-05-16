package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.phdlabs.sungwon.heyoo.model.HeyooEvent;

/**
 * Created by SungWon on 5/15/2017.
 */

public class EventRecyclerAdapter extends BaseListRecyclerAdapter<HeyooEvent, RecyclerView.ViewHolder> {


    @Override
    public int getItemViewType(int position) {
        return position;
    }



    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, HeyooEvent data, int position, int type) {
        switch (viewHolder.getItemViewType()){
            case 0:/*Title*/
                break;
            case 1:/*Image*/
                break;
            case 2:/*Statistics?*/
                break;
            case 3:/*People*/
                break;
            case 4:/*Attachementes*/
                break;
        }
    }

    @Override
    protected RecyclerView.ViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
        switch (type){
            case 0:/*Title*/
                break;
            case 1:/*Image*/
                break;
            case 2:/*Statistics?*/
                break;
            case 3:/*People*/
                break;
            case 4:/*Attachementes*/
                break;
        }
        return null;
    }
}
