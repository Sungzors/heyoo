package com.phdlabs.sungwon.heyoo.api.utility.gms;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;

import java.io.IOException;

/**
 * Created by SungWon on 7/21/2017.
 */

public class RegistrationService extends IntentService {

    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        InstanceID myID = InstanceID.getInstance(this);
        String registrationToken = "";
        try {
            registrationToken = myID.getToken(
                    "heyooandroid-174500",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Preferences pref = new Preferences(this);
        pref.putPreference(Constants.PreferenceConstants.GCM_TOKEN, registrationToken);
        Log.d("Registration token: ", registrationToken);

        GcmPubSub subscription = GcmPubSub.getInstance(this);
        try {
            subscription.subscribe(registrationToken, Constants.GcmTopics.MAIN, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
