package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.HeyooMedia;

/**
 * Created by SungWon on 6/28/2017.
 */

public class EventMediaPostResponse extends ErrorResponse {

    private HeyooMedia medium;

    public EventMediaPostResponse(HeyooMedia medium) {
        this.medium = medium;
    }

    public HeyooMedia getMedium() {
        return medium;
    }
}
