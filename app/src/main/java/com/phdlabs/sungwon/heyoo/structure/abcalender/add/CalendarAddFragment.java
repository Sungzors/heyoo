package com.phdlabs.sungwon.heyoo.structure.abcalender.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 6/18/2017.
 */

public class CalendarAddFragment extends BaseFragment<CalendarContract.Add.Controller>
        implements CalendarContract.Add.View, View.OnClickListener{

    private static final String TAG = "CalendarAddFragment";

    private EditText mCalendarName;
    private RadioGroup mRadioGroup;
    private TextView mAddPeople;
    private EditText mEmptyShare;
    private RecyclerView mRecycler;
    private Button mPublish;

//    private BaseListRecyclerAdapter<> TODO: make adapter when users more robust

    public static CalendarAddFragment newInstance(){
        Bundle args = new Bundle();
        CalendarAddFragment fragment = new CalendarAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected CalendarContract.Add.Controller createController() {
        return new CalendarAddController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_calendar_add;
    }

    @Override
    public void onStart() {
        super.onStart();
        getBaseActivity().setToolbarTitle(R.string.create_calendar);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarName = findById(R.id.fca_calendar_edit_name);
        mRadioGroup = findById(R.id.fca_radiogroup);
        mAddPeople = findById(R.id.fca_share_add);
        mEmptyShare = findById(R.id.fca_empty_share);
        mRecycler = findById(R.id.fca_share_list);
        mPublish = findById(R.id.fca_publish_button);
        mAddPeople.setOnClickListener(this);
        mPublish.setOnClickListener(this);
    }

    private int getColor(int id){
        switch (id){
            case R.id.fca_amethyst:
                return R.string.amethyst;
            case R.id.fca_butterscotch:
                return R.string.butterscotch;
            case R.id.fca_darkPink:
                return R.string.darkpink;
            case R.id.fca_dimYellow:
                return R.string.dimyellow;
            case R.id.fca_paleRed:
                return R.string.palered;
            case R.id.fca_seafoamBlue:
                return R.string.seafoamblue;
            case R.id.fca_seafoamGreen:
                return R.string.seafoamgreen;
            default:
                return R.string.butterscotch;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fca_share_add:
                break;
            case R.id.fca_publish_button:
                break;
        }
    }
}
