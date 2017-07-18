package com.phdlabs.sungwon.heyoo.api.response;

import com.phdlabs.sungwon.heyoo.model.HeyooAlert;

import java.util.List;

/**
 * Created by SungWon on 7/18/2017.
 */

public class AlertRetrievalResponse {

    List<HeyooAlert> alerts;

    public List<HeyooAlert> getAlerts() {
        return alerts;
    }
}
