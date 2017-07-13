package com.phdlabs.sungwon.heyoo.structure.abcalender.member;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.abcalender.CalendarContract;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;

/**
 * Created by SungWon on 7/13/2017.
 */

public class CalendarMemberFragment extends BaseFragment<CalendarContract.Member.Controller>
        implements CalendarContract.Member.View, View.OnClickListener{



    @NonNull
    @Override
    protected CalendarContract.Member.Controller createController() {
        return new CalendarMemberController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_calendar_member;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMemberClicked() {

    }

    @Override
    public void onAddClicked() {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fcm_calendar_add:
                break;
        }
    }


}
