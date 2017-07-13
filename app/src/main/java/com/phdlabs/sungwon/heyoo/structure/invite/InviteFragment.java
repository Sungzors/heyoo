package com.phdlabs.sungwon.heyoo.structure.invite;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.data.AttendeePostData;
import com.phdlabs.sungwon.heyoo.api.data.CalendarUserPostData;
import com.phdlabs.sungwon.heyoo.api.event.AttendeePostEvent;
import com.phdlabs.sungwon.heyoo.api.event.CalendarUserPostEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.event.UserRetrievalEvent;
import com.phdlabs.sungwon.heyoo.api.response.CalendarPostResponse;
import com.phdlabs.sungwon.heyoo.api.response.EventPostResponse;
import com.phdlabs.sungwon.heyoo.api.response.UserRetrievalResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;

/**
 * Created by SungWon on 7/5/2017.
 */

public class InviteFragment extends BaseFragment<InviteContract.Controller>
        implements InviteContract.View, LoaderManager.LoaderCallbacks<Cursor>, SearchView.OnQueryTextListener{

    android.widget.SearchView mSearchText;
    RecyclerView mSearchList;
    BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder> mAdapter;

    HeyooEndpoint mCaller;
    Preferences mPref;
    EventBus mEventBus;

    HeyooEvent mEvent;
    int mCalID = -1;

    final int DETAILS_QUERY_ID = 0;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final String SORT_ORDER = ContactsContract.CommonDataKinds.Phone.TYPE + " ASC ";
    private List<HeyooAttendee> mSelectionList = new ArrayList<>();
    private List<HeyooAttendee> mAttendeeList = new ArrayList<>();
    private List<HeyooAttendee> mFilteredList = new ArrayList<>();

    public static InviteFragment newInstance(HeyooEvent event){
        Bundle args = new Bundle();
        args.putSerializable(Constants.BundleKeys.EVENT_DETAIL, event);
        InviteFragment fragment = new InviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static InviteFragment newInstance(int calID){
        Bundle args = new Bundle();
        args.putInt(Constants.BundleKeys.HOME_CALENDAR_ID, calID);
        InviteFragment fragment = new InviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected InviteContract.Controller createController() {
        return new InviteController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    public void onStart() {
        super.onStart();
        getBaseActivity().setToolbarTitle("Invite People");
        MainActivity activity = (MainActivity)getBaseActivity();
        activity.showBackArrow(android.R.drawable.ic_menu_close_clear_cancel);
        Toolbar toolbar = activity.getToolbar();
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_check);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mEventBus.register(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()){
            case "Yes":
                onCheckSelected();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchText = findById(R.id.fi_search_text);
        mSearchList = findById(R.id.fi_search_list);
        mCaller = Rest.getInstance().getHeyooEndpoint();
        mPref = new Preferences(getContext());
        mEventBus = EventsManager.getInstance().getDataEventBus();
        mEvent = (HeyooEvent)getArguments().getSerializable(Constants.BundleKeys.EVENT_DETAIL);
        mCalID = getArguments().getInt(Constants.BundleKeys.HOME_CALENDAR_ID, -1);

        SearchManager searchManager = (SearchManager)getBaseActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchText.setSearchableInfo(searchManager.getSearchableInfo(getBaseActivity().getComponentName()));
        mSearchText.setQueryHint(getResources().getString(R.string.enter_name_phone_or_email));
        mSearchText.setSubmitButtonEnabled(true);
        mSearchText.setOnQueryTextListener(this);

        showList(mSelectionList);

        showProgress();
        showContacts();
        retrieveUsers();

    }

    @Subscribe
    public void onEvent(UserRetrievalEvent event){

    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getBaseActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getLoaderManager().initLoader(DETAILS_QUERY_ID, null, this);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                getLoaderManager().initLoader(DETAILS_QUERY_ID, null, this);
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        switch (id) {
            case DETAILS_QUERY_ID:
                return new CursorLoader(
                                getContext(),
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                projection,
                                null,
                                null,
                                SORT_ORDER
                        );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            try{
                while (cursor.moveToNext()){
                    String y = cursor.getString(1);
                    String yClean = y.replaceAll("[^0-9]+", "");
                    String x = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                    if (y.length()>7){
                        String lastName = "";
                        String firstName= "";
                        if(x.split("\\w+").length>1){

                            lastName = x.substring(x.lastIndexOf(" ")+1);
                            firstName = x.substring(0, x.lastIndexOf(' '));
                        }
                        else{
                            lastName = "";
                            firstName = x;
                        }
                        mAttendeeList.add(new HeyooAttendee(firstName, lastName, yClean, "+1", false));
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void retrieveUsers(){
        Call<UserRetrievalResponse> call = mCaller.getUsers(mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null));
        call.enqueue(new HCallback<UserRetrievalResponse, UserRetrievalEvent>(mEventBus) {
            @Override
            protected void onSuccess(UserRetrievalResponse data) {
                mAttendeeList.addAll(data.getUsers());
                hideProgress();
                mEventBus.post(new UserRetrievalEvent());
            }
        });
    }


    @Override
    public void onCheckSelected() {
        mSelectionList.clear();
        for (HeyooAttendee attendee : mAttendeeList){
            if(attendee.isChecked()){
                mSelectionList.add(attendee);
            }
        }
        if(mCalID == -1){
            if(mEvent == null){
                Toast.makeText(getContext(), "Event failed to pass. Returning to previous screen", Toast.LENGTH_SHORT).show();
                getBaseActivity().onBackPressed();
            }
            AttendeePostData data = new AttendeePostData(mSelectionList);
            Call<EventPostResponse> call = mCaller.postAttendees(mEvent.getId(), mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null), data);
            call.enqueue(new HCallback<EventPostResponse, AttendeePostEvent>(mEventBus) {
                @Override
                protected void onSuccess(EventPostResponse data) {
                    mEventBus.post(new AttendeePostEvent());
                    getBaseActivity().onBackPressed();
                }

            });
        } else {
            CalendarUserPostData data = new CalendarUserPostData(mSelectionList);
            Call<CalendarPostResponse> call = mCaller.postCalendarUsers(mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null), mCalID, data);
            call.enqueue(new HCallback<CalendarPostResponse, CalendarUserPostEvent>(mEventBus) {
                @Override
                protected void onSuccess(CalendarPostResponse data) {
                    mEventBus.post(new CalendarPostResponse());
                    getBaseActivity().onBackPressed();
                }
            });
        }

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mFilteredList.clear();
        for (HeyooAttendee attendee : mAttendeeList){
            if(attendee.getFirst_name().toLowerCase().contains(s.toLowerCase())||attendee.getLast_name().toLowerCase().contains(s.toLowerCase())||attendee.getPhone().contains(s.toLowerCase())){
                mFilteredList.add(attendee);

            }
        }
        if(mAdapter.getItemCount()>0){
            mAdapter.clear();
        }
        Collections.sort(mFilteredList, new Comparator<HeyooAttendee>() {
            @Override
            public int compare(HeyooAttendee e1, HeyooAttendee e2) {
                return e1.getFirst_name().compareTo(e2.getFirst_name());
            }
        });

        mAdapter.setItems(mFilteredList);
        mAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
//        mFilteredList.clear();
//        for (HeyooAttendee attendee : mAttendeeList){
//            if(attendee.getFirst_name().toLowerCase().contains(s.toLowerCase())||attendee.getLast_name().toLowerCase().contains(s.toLowerCase())||attendee.getPhone().contains(s.toLowerCase())){
//                mFilteredList.add(attendee);
//
//            }
//        }
//        mAdapter.clear();
//        mAdapter.setItems(mFilteredList);
//        mAdapter.notifyDataSetChanged();
        //if used, make sure to include sort
        return false;
    }

    @Override
    public void showList(List<HeyooAttendee> list) {
        mAdapter = new BaseListRecyclerAdapter<HeyooAttendee, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, HeyooAttendee data, int position, int type) {
                bindItemViewHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_invite, inflater, parent){
                };
            }
        };
        mAdapter.setItems(list);
        mSearchList.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mSearchList.setAdapter(mAdapter);
    }

    private void bindItemViewHolder(final BaseViewHolder viewHolder, final HeyooAttendee data) {
        final ToggleButton selectButton = viewHolder.get(R.id.cvi_button_selected);
        TextView attendeeName = viewHolder.get(R.id.cvi_attendee_name);
        ImageView attendeeIcon = viewHolder.get(R.id.cvi_attendee_icon);
        TextView attendeeStatus = viewHolder.get(R.id.cvi_attendee_status);
        ImageView attendeeStatusIcon = viewHolder.get(R.id.cvi_attendee_status_icon);
        attendeeName.setText(data.getFirst_name() + " " + data.getLast_name());

        selectButton.setChecked(data.isChecked());
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = data.getPhone();
                for (HeyooAttendee attendee : mAttendeeList){
                    if (attendee.getPhone().equals(phone)){
                        attendee.setChecked(selectButton.isChecked());
                        break;
                    }
                }
            }
        });
        Picasso.with(getContext()).load(data.getProfile_picture()).placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.pandapic)).into(attendeeIcon);
        if(data.isVerified()){
            attendeeStatus.setText("Heyoo Member");
            Picasso.with(getContext()).load(R.drawable.ic_heyoomembertwocircles).into(attendeeStatusIcon);
        } else {
            attendeeStatus.setText("Contacts");
            Picasso.with(getContext()).load(R.drawable.ic_contacts).into(attendeeStatusIcon);
        }
        TextView attendeeView = viewHolder.get(R.id.cvi_attendee_view);
        attendeeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "view clicked "+ data.getPhone(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
