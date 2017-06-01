package com.phdlabs.sungwon.heyoo.structure.aahome.eventedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.structure.core.BaseActivity;
import com.phdlabs.sungwon.heyoo.utility.Constants;

/**
 * Created by SungWon on 5/31/2017.
 */

public class EventEditActivity extends BaseActivity {

    HeyooEvent mEvent;

    @Override
    protected int layoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected int contentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEvent = (HeyooEvent)getIntent().getSerializableExtra(Constants.BundleKeys.EVENT_DETAIL);
        showEditFragment();
    }

    private void showEditFragment(){
        replaceFragment(EventEditFragment.newInstance(mEvent), true);
    }

    public static void start(Context context, HeyooEvent event){
        Intent intent = new Intent(context, EventEditActivity.class);
        intent.putExtra(Constants.BundleKeys.EVENT_DETAIL, event);
        context.startActivity(intent);
    }
}
