package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;

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
    protected RecyclerView.ViewHolder viewHolder(LayoutInflater inflater, final ViewGroup parent, int type) {
        switch (type){
            case 0:/*Title*/
                return new BaseViewHolder(R.layout.card_view_event_title, inflater, parent){
                    @Override
                    protected void addClicks(ViewMap views) {
                        views.click(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch(view.getId()){
                                    case R.id.cvet_yes_button:
                                        Toast.makeText(parent.getContext(), "Yes button clicked", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.cvet_maybe_button:
                                        Toast.makeText(parent.getContext(), "Maybe button clicked", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.cvet_no_button:
                                        Toast.makeText(parent.getContext(), "No button clicked", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvet_event_title, R.id.cvet_event_date, R.id.cvet_yes_button, R.id.cvet_maybe_button, R.id.cvet_no_button);
                    }
                };
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

    private void bindTitleHolder(BaseViewHolder baseViewHolder, HeyooEvent event){

    }
}
