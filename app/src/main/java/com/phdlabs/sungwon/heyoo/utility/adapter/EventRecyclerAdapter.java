package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import com.phdlabs.sungwon.heyoo.structure.acevents.event.EventContract;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.CustomLinearLayoutManager;
import com.phdlabs.sungwon.heyoo.utility.ImageExpander;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/15/2017.
 */

public class EventRecyclerAdapter extends BaseListRecyclerAdapter<HeyooEvent, BaseViewHolder>
        implements View.OnClickListener{

    EventContract.Controller mController;

    List<ImageView> mImageDisplayList;

    public EventRecyclerAdapter(@NonNull List<HeyooEvent> values, EventContract.Controller mController) {
        super(values);
        this.mController = mController;

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
                return new BaseViewHolder(R.layout.card_view_event_images, inflater, parent) {
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
        Spinner calendarSpinner = baseViewHolder.get(R.id.cvete_fidget_spinner);
        DefaultSpinnerAdapter<HeyooCalendar> spinnerAdapter = new DefaultSpinnerAdapter<HeyooCalendar>(mController.getContext(), mController.getSelectedCalendar(), R.layout.spinner_calendar_list){
            @Override
            public void bindView(View view, HeyooCalendar data, int position) {
                GradientDrawable circleOfDoom = (GradientDrawable)(view.findViewById(R.id.scl_blue_dot_of_death_knell)).getBackground();
                circleOfDoom.setColor(Constants.getColor(data.getColor()));
                ((TextView)view.findViewById(R.id.scl_text_of_deadliness)).setText(data.getName());
            }
        };
        calendarSpinner.setAdapter(spinnerAdapter);
        calendarSpinner.setEnabled(false);
        calendarSpinner.setBackgroundResource(0);
        ((TextView)baseViewHolder.get(R.id.cvete_event_title)).setText(event.getName());
        TextView startDate = baseViewHolder.get(R.id.cvete_start_date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa");
        startDate.setText(sdf.format(event.getStart_time()));
        TextView endDate = baseViewHolder.get(R.id.cvete_end_date);
        endDate.setText(sdf.format(event.getEnd_time()));
        ((ToggleButton)baseViewHolder.get(R.id.cvete_toggle_allday)).setChecked(event.isAllDay());
        baseViewHolder.get(R.id.cvete_toggle_allday).setEnabled(false);
        //TODO: check for repeat events
//        (baseViewHolder.get(R.id.cvet_yes_button)).setOnClickListener(this);
//        (baseViewHolder.get(R.id.cvet_maybe_button)).setOnClickListener(this);
//        (baseViewHolder.get(R.id.cvet_no_button)).setOnClickListener(this);
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
            baseViewHolder.get(R.id.cvei_empty_prompt).setVisibility(View.VISIBLE);
        } else {
            baseViewHolder.get(R.id.cvei_container).setVisibility(View.VISIBLE);
            baseViewHolder.get(R.id.cvei_empty_prompt).setVisibility(View.GONE);
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
    }

    private void bindPeopleHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        baseViewHolder.get(R.id.cvep_add_button).setOnClickListener(this);
        List<HeyooAttendee> attendeesList = mController.getAssociatedAttendees();
        List<HeyooAttendee> goingList = new ArrayList<>();
        for (int i = 0; i < attendeesList.size(); i++) {
            if (attendeesList.get(i).getAttendees().getStatus().equals("yes")){
                goingList.add(attendeesList.get(i));
            }
        }
        List<HeyooAttendee> maybeList = new ArrayList<>();
        for (int i = 0; i < attendeesList.size(); i++) {
            if (attendeesList.get(i).getAttendees().getStatus().equals("maybe")){
                maybeList.add(attendeesList.get(i));
            }
        }
        List<HeyooAttendee> noList = new ArrayList<>();
        for (int i = 0; i < attendeesList.size(); i++) {
            if (attendeesList.get(i).getAttendees().getStatus().equals("no")){
                noList.add(attendeesList.get(i));
            }
        }
        List<HeyooAttendee> noReplyList = new ArrayList<>();
        for (int i = 0; i < attendeesList.size(); i++) {
            if (attendeesList.get(i).getAttendees().getStatus().equals("pending")){
                noReplyList.add(attendeesList.get(i));
            }
        }
        ((TextView) baseViewHolder.get(R.id.cvep_going_text)).setText("Going (" + goingList.size() + ")");
        ((TextView) baseViewHolder.get(R.id.cvep_maybe_text)).setText("Maybe (" + maybeList.size() + ")");
        ((TextView) baseViewHolder.get(R.id.cvep_no_text)).setText("No (" + noList.size() + ")");
        ((TextView) baseViewHolder.get(R.id.cvep_noreply_text)).setText("Have Not Replied (" + noReplyList.size() + ")");
        BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder> goingAdapter = new BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooAttendee data, int position, int type) {
                bindAttendeeHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_event_people_attendee, inflater, parent){
                    @Override
                    protected void addClicks(ViewMap views) {
                    }

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvepa_attendee_icon, R.id.cvepa_attendee_name, R.id.cvepa_attendee_status, R.id.cvepa_attendee_colortab);
                    }
                };
            }
        };
        BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder> maybeAdapter = new BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooAttendee data, int position, int type) {
                bindAttendeeHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_event_people_attendee, inflater, parent){
                    @Override
                    protected void addClicks(ViewMap views) {
                    }

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvepa_attendee_icon, R.id.cvepa_attendee_name, R.id.cvepa_attendee_status, R.id.cvepa_attendee_colortab);
                    }
                };
            }
        };;
        BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder> noAdapter = new BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooAttendee data, int position, int type) {
                bindAttendeeHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_event_people_attendee, inflater, parent){
                    @Override
                    protected void addClicks(ViewMap views) {
                    }

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvepa_attendee_icon, R.id.cvepa_attendee_name, R.id.cvepa_attendee_status, R.id.cvepa_attendee_colortab);
                    }
                };
            }
        };
        BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder> noReplyAdapter = new BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooAttendee data, int position, int type) {
                bindAttendeeHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_event_people_attendee, inflater, parent){
                    @Override
                    protected void addClicks(ViewMap views) {
                    }

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvepa_attendee_icon, R.id.cvepa_attendee_name, R.id.cvepa_attendee_status, R.id.cvepa_attendee_colortab);
                    }
                };
            }
        };
        goingAdapter.setItems(goingList);
        maybeAdapter.setItems(maybeList);
        noAdapter.setItems(noList);
        noReplyAdapter.setItems(noReplyList);
        RecyclerView goingRecycler = baseViewHolder.get(R.id.cvep_going_list);
        RecyclerView maybeRecycler = baseViewHolder.get(R.id.cvep_maybe_list);
        RecyclerView noRecycler = baseViewHolder.get(R.id.cvep_no_list);
        RecyclerView noReplyRecycler = baseViewHolder.get(R.id.cvep_noreply_list);
        goingRecycler.setLayoutManager(new CustomLinearLayoutManager(mController.getContext()));
        goingRecycler.setAdapter(goingAdapter);
        maybeRecycler.setLayoutManager(new CustomLinearLayoutManager(mController.getContext()));
        maybeRecycler.setAdapter(maybeAdapter);
        noRecycler.setLayoutManager(new CustomLinearLayoutManager(mController.getContext()));
        noRecycler.setAdapter(noAdapter);
        noReplyRecycler.setLayoutManager(new CustomLinearLayoutManager(mController.getContext()));
        noReplyRecycler.setAdapter(noReplyAdapter);
    }

    private void bindAttendeeHolder(BaseViewHolder baseViewHolder, HeyooAttendee attendee){
        ImageExpander expander = new ImageExpander(mController.getContext());
        expander.displayRoundedImage(attendee.getProfile_picture(), (ImageView)baseViewHolder.get(R.id.cvepa_attendee_icon));
        ((TextView)baseViewHolder.get(R.id.cvepa_attendee_name)).setText(attendee.getFirst_name() + " " + attendee.getLast_name());
        if(attendee.isVerified()){
            ((TextView)baseViewHolder.get(R.id.cvepa_attendee_status)).setText("Heyoo Member");
            ((ImageView)baseViewHolder.get(R.id.cvepa_attendee_colortab)).setImageResource(R.drawable.ic_heyoomembertwocircles);
        } else {
            ((TextView)baseViewHolder.get(R.id.cvepa_attendee_status)).setText("Contacts");
            ((ImageView)baseViewHolder.get(R.id.cvepa_attendee_colortab)).setImageResource(R.drawable.ic_contacts);
        }

    }

    private void bindAttachmentHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        ((TextView)baseViewHolder.get(R.id.cvea_attachment_title)).setText("Attachments (0)");
        (baseViewHolder.get(R.id.cvea_add_button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
//            case R.id.cvet_yes_button:
//                Toast.makeText(mController.getContext(), "Yes button clicked", Toast.LENGTH_SHORT).show();
//                mController.onTitleYesClicked();
//                break;
//            case R.id.cvet_maybe_button:
//                Toast.makeText(mController.getContext(), "Maybe button clicked", Toast.LENGTH_SHORT).show();
//                mController.onTitleMaybeClicked();
//                break;
//            case R.id.cvet_no_button:
//                Toast.makeText(mController.getContext(), "No button clicked", Toast.LENGTH_SHORT).show();
//                mController.onTitleNoClicked();
//                break;
            case R.id.cvep_add_button:
                mController.onAttendeeAddClicked();
                break;
            case R.id.cvei_add_button:
                mController.onMediaAddClicked();
                break;
            case R.id.cvea_add_button:
                Toast.makeText(mController.getContext(), "Add button for Attachment clicked", Toast.LENGTH_SHORT).show();
                mController.onAttachmentAddClicked();
                break;
        }
    }
}
