package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.structure.aahome.eventedit.EventEditContract;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.HeyooDatePicker;
import com.phdlabs.sungwon.heyoo.utility.ImageExpander;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SungWon on 5/30/2017.
 */

public class EventEditRecyclerAdapter extends BaseListRecyclerAdapter<HeyooEvent, BaseViewHolder>
        implements View.OnClickListener{

    EventEditContract.Controller mController;

    EditText mTitle;
    EditText mStartDate;
    HeyooDatePicker mStartPicker;
    Calendar mStartCalendar;
    EditText mEndDate;
    HeyooDatePicker mEndPicker;
    Calendar mEndCalendar;
    ToggleButton mAllDay, mRepeat;
    List<HeyooMedia> mMediaList = new ArrayList<>();
    List<ImageView> mImageDisplayList;
    EditText mLocation;
    EditText mNotes;
    EditText mCalendar;

    boolean isNull = true;


    public EventEditRecyclerAdapter(@NonNull List<HeyooEvent> values, EventEditContract.Controller mController) {
        super(values);
        this.mController = mController;
        if (values.get(0)!=null){
            isNull = false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        mStartDate = baseViewHolder.get(R.id.cvete_start_date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa");
        mStartDate.setText(sdf.format(new Date()));
        mEndDate = baseViewHolder.get(R.id.cvete_end_date);
        mEndDate.setText(sdf.format(new Date()));
        mStartPicker = new HeyooDatePicker(mStartDate, mController.getContext());
        mEndPicker = new HeyooDatePicker(mEndDate, mController.getContext());
        if(!isNull){
            mTitle.setText(event.getName());
        }
        mStartCalendar = mStartPicker.getTimePicker().getMyCalendar();
        mEndCalendar = mEndPicker.getTimePicker().getMyCalendar();
    }

    private void bindImageHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        List<HeyooMedia> mediaList = mController.getAssociatedMedia();
        ((TextView) baseViewHolder.get(R.id.cvei_image_title)).setText("Event Media (" +mediaList.size() +")");
        (baseViewHolder.get(R.id.cvei_add_button)).setOnClickListener(this);
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < mediaList.size(); i++) {
            urlList.add(mediaList.get(i).getFile_path());
        }
        if(mediaList.size()== 0){
            baseViewHolder.get(R.id.cvei_container).setVisibility(View.GONE);
        } else {
            baseViewHolder.get(R.id.cvei_container).setVisibility(View.VISIBLE);
        }
        ImageExpander expander = new ImageExpander(mController.getContext(), urlList);
        mImageDisplayList = expander.insertExpandingImage(baseViewHolder.get(R.id.cvei_container));
        for (int i = 0; i < mImageDisplayList.size(); i++) {
            final int x = i + 1;
            mImageDisplayList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mController.getContext(), "Image Selected: " + x, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void bindStatisticsHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        mLocation = baseViewHolder.get(R.id.cves_event_address);
        mLocation.setSingleLine(false);
        mLocation.setTextColor(ContextCompat.getColor(mController.getContext(),R.color.black));
        mNotes = baseViewHolder.get(R.id.cves_event_status);
        mNotes.setSingleLine(false);
        mNotes.setTextColor(ContextCompat.getColor(mController.getContext(),R.color.black));
        mCalendar = baseViewHolder.get(R.id.cves_calendar_name);
        mCalendar.setTextColor(ContextCompat.getColor(mController.getContext(),R.color.black));
        if (!isNull){
            mLocation.setText(event.getAddress());
            mNotes.setText(event.getDescription());
            mCalendar.setText("Main Calendar");
        }
    }

    private void bindPeopleHolder(BaseViewHolder baseViewHolder, HeyooEvent event){

    }

    private void bindAttendeeHolder(BaseViewHolder baseViewHolder, HeyooAttendee attendee){
    }

    private void bindAttachmentHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        ((TextView)baseViewHolder.get(R.id.cvea_attachment_title)).setText("Attachments (0)");
        (baseViewHolder.get(R.id.cvea_add_button)).setOnClickListener(this);
    }

    public HeyooEvent getEvent(){
        HeyooEvent event = new HeyooEvent(
                mController.getEventid(),
                mTitle.getText().toString(),
                mStartCalendar.getTime(),
                mEndCalendar.getTime(),
                mNotes.getText().toString(),
                true,
                0, //TODO: retrieve Calendar id
                mLocation.getText().toString() );
        return event;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.cvei_add_button:
                mController.onMediaAddClicked();
                break;
        }
    }
}
