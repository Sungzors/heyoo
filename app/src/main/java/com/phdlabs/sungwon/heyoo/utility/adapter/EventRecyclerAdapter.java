package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;
import com.phdlabs.sungwon.heyoo.structure.aahome.event.EventContract;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.ImageExpander;
import com.phdlabs.sungwon.heyoo.utility.ViewMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
                return new BaseViewHolder(R.layout.card_view_event_title, inflater, parent){


                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvet_event_title, R.id.cvet_event_date, R.id.cvet_yes_button, R.id.cvet_maybe_button, R.id.cvet_no_button);
                    }
                };
            case 1:/*Image*/
                return new BaseViewHolder(R.layout.card_view_event_images, inflater, parent){

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvei_image_title, R.id.cvei_add_button, R.id.cvei_container, R.id.cvei_record_orange);
                    }
                };
            case 2:/*Statistics?*/
                return new BaseViewHolder(R.layout.card_view_event_statistics, inflater, parent){

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cves_event_address, R.id.cves_event_status, R.id.cves_calendar_name, R.id.cves_add_button, R.id.cves_reminder_container, R.id.cves_calendar_icon, R.id.cves_description_icon, R.id.cves_location_icon, R.id.cves_reminder_icon);
                    }
                };
            case 3:/*People*/
                return new BaseViewHolder(R.layout.card_view_event_people, inflater, parent){

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvep_going_text, R.id.cvep_going_list, R.id.cvep_maybe_list, R.id.cvep_maybe_text, R.id.cvep_no_list, R.id.cvep_no_text, R.id.cvep_noreply_list, R.id.cvep_noreply_text, R.id.cvep_people_icon);
                    }
                };
            case 4:/*Attachementes*/
                return new BaseViewHolder(R.layout.card_view_event_attachment, inflater, parent){

                    @Override
                    protected void putViewsIntoMap(ViewMap views) {
                        views.put(R.id.cvea_attachment_title, R.id.cvea_add_button, R.id.cvea_attachment_list, R.id.cvea_attachment_icon);
                    }
                };
        }
        return null;
    }

    private void bindTitleHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        ((TextView)baseViewHolder.get(R.id.cvet_event_title)).setText(event.getDescription());
        if (event.getStartTimeHash() == HeyooEvent.hashCode(Calendar.getInstance())) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
            ((TextView) baseViewHolder.get(R.id.cvet_event_date)).setText("Today - " + formatter.format(event.getStartCalendar().getTime()));
        } else if(event.getStartTimeHash() == HeyooEvent.hashCode(Calendar.getInstance()) + 1){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
            ((TextView) baseViewHolder.get(R.id.cvet_event_date)).setText("Tomorrow - " + formatter.format(event.getStartCalendar().getTime()));
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE - MMM dd");
            ((TextView)baseViewHolder.get(R.id.cvet_event_date)).setText(formatter.format(event.getStartCalendar().getTime()));
        }
        (baseViewHolder.get(R.id.cvet_yes_button)).setOnClickListener(this);
        (baseViewHolder.get(R.id.cvet_maybe_button)).setOnClickListener(this);
        (baseViewHolder.get(R.id.cvet_no_button)).setOnClickListener(this);
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
        ((TextView) baseViewHolder.get(R.id.cves_event_address)).setText(event.getAddress());
        ((TextView) baseViewHolder.get(R.id.cves_event_status)).setText(event.getDescription());
        ((TextView) baseViewHolder.get(R.id.cves_calendar_name)).setText("Main Calendar");
    }

    private void bindPeopleHolder(BaseViewHolder baseViewHolder, HeyooEvent event){
        List<HeyooAttendee> attendeesList = mController.getAssociatedAttendees();
    }

    private void bindAttachmentHolder(BaseViewHolder baseViewHolder, HeyooEvent event){

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cvet_yes_button:
                Toast.makeText(mController.getContext(), "Yes button clicked", Toast.LENGTH_SHORT).show();
                mController.onTitleYesClicked();
                break;
            case R.id.cvet_maybe_button:
                Toast.makeText(mController.getContext(), "Maybe button clicked", Toast.LENGTH_SHORT).show();
                mController.onTitleMaybeClicked();
                break;
            case R.id.cvet_no_button:
                Toast.makeText(mController.getContext(), "No button clicked", Toast.LENGTH_SHORT).show();
                mController.onTitleNoClicked();
                break;
            case R.id.cvei_add_button:
                Toast.makeText(mController.getContext(), "Add button clicked", Toast.LENGTH_SHORT).show();
                mController.onMediaAddClicked();
                break;
        }
    }
}
