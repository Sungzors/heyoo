package com.phdlabs.sungwon.heyoo.structure.invite;

import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;
import com.phdlabs.sungwon.heyoo.structure.core.Contract;

import java.util.List;

/**
 * Created by SungWon on 7/5/2017.
 */

public interface InviteContract {

    interface View extends Contract.BaseView{
        void showList(List<HeyooAttendee> list);
        void onCheckSelected();
    }

    interface Controller extends Contract.BaseController{

    }
}
