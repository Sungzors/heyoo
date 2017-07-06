package com.phdlabs.sungwon.heyoo.structure.invite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.structure.mainactivity.MainActivity;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_confirm:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchText = findById(R.id.fi_search_text);
        mSearchList = findById(R.id.fi_search_list);
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
