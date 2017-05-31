package com.phdlabs.sungwon.heyoo.structure.aahome.eventedit;

import android.content.Context;

import com.phdlabs.sungwon.heyoo.structure.core.Contract;
import com.phdlabs.sungwon.heyoo.utility.adapter.EventEditRecyclerAdapter;

/**
 * Created by SungWon on 5/30/2017.
 */

public interface EventEditContract {

    interface View extends Contract.BaseView{
        void showEventEdit();
    }

    interface Controller extends Contract.BaseController{
        void onPublishClicked(EventEditRecyclerAdapter adapter);
        void onSaveDraftClicked(EventEditRecyclerAdapter adapter);

        Context getContext();

    }
}
