package com.phdlabs.sungwon.heyoo.structure.invite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.Constants;

import java.util.List;

/**
 * Created by SungWon on 7/5/2017.
 */

public class InviteFragment extends BaseFragment<InviteContract.Controller>
        implements InviteContract.View, View.OnClickListener{

    EditText mSearchText;
    RecyclerView mSearchList;

    public static InviteFragment newInstance(HeyooEvent event){
        Bundle args = new Bundle();
        args.putSerializable(Constants.BundleKeys.EVENT_DETAIL, event);
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showList(List<HeyooAttendee> list) {

    }

    @Override
    public void onCheckSelected() {

    }

    @Override
    public void onClick(View view) {

    }

}
