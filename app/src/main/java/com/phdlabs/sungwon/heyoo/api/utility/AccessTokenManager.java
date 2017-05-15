package com.phdlabs.sungwon.heyoo.api.utility;

import com.phdlabs.sungwon.heyoo.utility.GsonHolder;
import com.phdlabs.sungwon.heyoo.utility.Preferences;

import static com.phdlabs.sungwon.heyoo.utility.Constants.PreferenceConstants.KEY_TOKEN;

/**
 * Created by SungWon on 5/15/2017.
 */

public class AccessTokenManager {

    private Preferences preferences;

    public AccessToken userToken() {
        return GsonHolder.get().fromJson(preferences.getPreferenceString(KEY_TOKEN, null), AccessToken.class);
    }

    public void storeUserToken(AccessToken token) {
        preferences.putPreference(KEY_TOKEN, GsonHolder.get().toJson(token));
    }
}
