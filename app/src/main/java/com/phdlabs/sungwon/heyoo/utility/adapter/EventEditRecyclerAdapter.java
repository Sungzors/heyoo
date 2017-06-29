package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooCalendar;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.structure.acevents.eventedit.EventEditContract;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.Constants;
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
    TextView mStartDate;
    HeyooDatePicker mStartPicker;
    Calendar mStartCalendar;
    TextView mEndDate;
    HeyooDatePicker mEndPicker;
    Calendar mEndCalendar;
    ToggleButton mAllDay, mRepeat;
    List<HeyooMedia> mMediaList = new ArrayList<>();
    List<ImageView> mImageDisplayList;
    EditText mLocation;
    EditText mNotes;
    Spinner mCalendarSpinner;

    boolean isNull = true;
    HeyooEvent mEvent;


    public EventEditRecyclerAdapter(@NonNull List<HeyooEvent> values, EventEditContract.Controller mController) {
        super(values);
        this.mController = mController;
        if (values.get(0)!=null){
            isNull = false;
            mEvent = values.get(0);
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

    private void bindTitleHolder(final BaseViewHolder baseViewHolder, HeyooEvent event){
        mCalendarSpinner = baseViewHolder.get(R.id.cvete_fidget_spinner);
        DefaultSpinnerAdapter<HeyooCalendar> spinnerAdapter = new DefaultSpinnerAdapter<HeyooCalendar>(mController.getContext(), mController.getCalendars(), R.layout.spinner_calendar_list){
            @Override
            public void bindView(View view, HeyooCalendar data, int position) {
                GradientDrawable circleOfDoom = (GradientDrawable)(view.findViewById(R.id.scl_blue_dot_of_death_knell)).getBackground();
                circleOfDoom.setColor(Constants.getColor(data.getColor()));
                ((TextView)view.findViewById(R.id.scl_text_of_deadliness)).setText(data.getName());
            }
        };
        mCalendarSpinner.setAdapter(spinnerAdapter);
        if(!isNull){
            mCalendarSpinner.setSelection(mController.getSelectedPosition(event.getCalendars()));
        }
        mTitle = baseViewHolder.get(R.id.cvete_event_title);
        mStartDate = baseViewHolder.get(R.id.cvete_start_date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa");
        if(!isNull){
            mStartDate.setText(sdf.format(event.getStart_time()));
        } else {
            mStartDate.setText(sdf.format(new Date()));
        }
        mEndDate = baseViewHolder.get(R.id.cvete_end_date);
        if(!isNull){
            mEndDate.setText(sdf.format(event.getEnd_time()));
        } else {
            mEndDate.setText(sdf.format(new Date()));
        }
        mStartDate.setOnClickListener(this);
        mEndDate.setOnClickListener(this);
        if(!isNull){
            mTitle.setText(event.getName());
        }
        mAllDay = baseViewHolder.get(R.id.cvete_toggle_allday);
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
        if (!isNull){
            mLocation.setText(event.getAddress());
            mNotes.setText(event.getDescription());
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
        Date startDate;
        Date endDate;
        if(isNull){
            startDate = new Date();
            endDate = new Date();
        } else {
            startDate = mEvent.getStart_time();
            endDate = mEvent.getEnd_time();
        }
        if(mStartPicker != null){
            if(mStartPicker.isFinished){
                startDate = mStartPicker.getTimePicker().getMyCalendar().getTime();
            }
        }
        if(mEndPicker != null){
            if(mEndPicker.isFinished){
                endDate = mEndPicker.getTimePicker().getMyCalendar().getTime();
            }
        }
        String notes;
        if (isNull){
            notes = null;
        } else {
            notes = mEvent.getDescription();
        }
        if(mNotes != null){
            if(TextUtils.isEmpty(mNotes.getText().toString())){
                notes = mNotes.getText().toString();
            }
        }
        int calID;
        calID = ((HeyooCalendar)mCalendarSpinner.getSelectedItem()).getId();

        String location;
        if(isNull){
            location = null;
        } else {
            location = mEvent.getAddress();
        }

        if(mLocation != null){
            location = mLocation.getText().toString();
        }
        HeyooEvent event = new HeyooEvent(
                mController.getEventid(),
                mTitle.getText().toString(),
                startDate,
                endDate,
                notes,
                true,
                calID,
                location );
        if (mAllDay.getText().toString().equals("")){
            event.setAllDay(false);
        } else {
            event.setAllDay(true);
        }
        return event;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.cvei_add_button:
                mController.onMediaAddClicked();
                break;
            case R.id.cvete_start_date:
                mStartPicker = new HeyooDatePicker(mStartDate, mController.getContext());
                break;
            case R.id.cvete_end_date:
                mEndPicker = new HeyooDatePicker(mEndDate, mController.getContext());
                break;
        }
    }
}
