package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;

import java.util.List;

/**
 * Created by SungWon on 7/9/2017.
 */

public class UserRetrievalResponse {

    List<HeyooAttendee> users;

    public List<HeyooAttendee> getUsers() {
        return users;
    }
}
