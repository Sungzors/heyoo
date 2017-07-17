package com.phdlabs.sungwon.heyoo.structure.member;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by SungWon on 7/16/2017.
 */

public class MemberViewFragment extends BaseFragment<MemberViewContract.Controller>
        implements MemberViewContract.View{

    public static MemberViewFragment newInstance(HeyooAttendee attendee){
        Bundle args = new Bundle();
        args.putSerializable(Constants.BundleKeys.ATTENDEE_DETAIL, attendee);
        MemberViewFragment fragment = new MemberViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected MemberViewContract.Controller createController() {
        return new MemberViewController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_member_view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HeyooAttendee attendee = (HeyooAttendee) getArguments().getSerializable(Constants.BundleKeys.ATTENDEE_DETAIL);
        Picasso.with(getContext()).load(attendee.getProfile_picture()).placeholder(R.drawable.pandapic).into(((ImageView)findById(R.id.fmv_avatar)));
        ((TextView)findById(R.id.fmv_name)).setText(attendee.getFirst_name() + " " + attendee.getLast_name());
        ((TextView)findById(R.id.fmv_location)).setText(attendee.getPhone());
        (findById(R.id.fmv_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
