package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.structure.aahome.eventedit.EventEditContract;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/30/2017.
 */

public class EventEditRecyclerAdapter extends BaseListRecyclerAdapter<HeyooEvent, BaseViewHolder>
        implements View.OnClickListener{

    EventEditContract.Controller mController;

    EditText mTitle;
    EditText mStartDate;
    EditText mEndDate;
    Button mAllDay;
    Button mRepeat;
    List<HeyooMedia> mMediaList = new ArrayList<>();
    EditText mLocation;
    EditText mNotes;


    public EventEditRecyclerAdapter(@NonNull List<HeyooEvent> values, EventEditContract.Controller mController) {
        super(values);
        this.mController = mController;
    }

    @Override
    protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooEvent data, int position, int type) {
        switch (viewHolder.getItemViewType()){
            case 0:/*Title*/
                bindTitleHolder(viewHolder, data);
                break;
            case 1:/*Image*/
                bindImageHolder(viewHolder, data);
                break;
            case 2:/*Statistics?*/
                bindStatisticsHolder(viewHolder, data);
                break;
            case 3:/*People*/
                bindPeopleHolder(viewHolder, data);
                break;
            case 4:/*Attachémentes*/
                bindAttachmentHolder(viewHolder, data);
                break;
        }
    }



    @Override
    protected BaseViewHolder viewHolder(LayoutInflater inflater, final ViewGroup parent, int type) {
        switch (type){
            case 0:/*Title*/
                return new BaseViewHolder(R.layout.card_view_event_title_edit, inflater, parent){
                };
            case 1:/*Image*/
                return new BaseViewHolder(R.layout.card_view_event_images, inflater, parent){
                };
            case 2:/*Statistics?*/
                return new BaseViewHolder(R.layout.card_view_event_statistics, inflater, parent){
                };
            case 3:/*People*/
                return new BaseViewHolder(R.layout.card_view_event_people, inflater, parent){
                };
            case 4:/*Attachementes*/
                return new BaseViewHolder(R.layout.card_view_event_attachment, inflater, parent){
                };
        }
        return null;
    }

    private void bindTitleHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        mTitle = baseViewHolder.get(R.id.cvete_event_title);
    }

    private void bindImageHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
    }

    private void bindStatisticsHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        EditText address = baseViewHolder.get(R.id.cves_event_address);
        address.setSingleLine(false);
        address.setEnabled(false);
        address.setTextColor(ContextCompat.getColor(mController.getContext(),R.color.black));
        address.setText(event.getAddress());
        EditText status = baseViewHolder.get(R.id.cves_event_status);
        status.setSingleLine(false);
        status.setEnabled(false);
        status.setTextColor(ContextCompat.getColor(mController.getContext(),R.color.black));
        status.setText(event.getDescription());
        EditText calendar = baseViewHolder.get(R.id.cves_calendar_name);
        calendar.setSingleLine(false);
        calendar.setEnabled(false);
        calendar.setTextColor(ContextCompat.getColor(mController.getContext(),R.color.black));
        ((TextView) baseViewHolder.get(R.id.cves_calendar_name)).setText("Main Calendar");
    }

    private void bindPeopleHolder(BaseViewHolder baseViewHolder, HeyooEvent event){

    }

    private void bindAttendeeHolder(BaseViewHolder baseViewHolder, HeyooAttendee attendee){
    }

    private void bindAttachmentHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        ((TextView)baseViewHolder.get(R.id.cvea_attachment_title)).setText("Attachments (0)");
        (baseViewHolder.get(R.id.cvea_add_button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
        }
    }
}
