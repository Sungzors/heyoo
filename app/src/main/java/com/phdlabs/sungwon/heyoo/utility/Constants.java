package com.phdlabs.sungwon.heyoo.utility;

import android.graphics.Color;

/**
 * Created by SungWon on 5/15/2017.
 */

public class Constants {

    public static final String AUTHORIZATION = "x-access-token";

    public interface PreferenceConstants {
        String KEY_TOKEN = "token";
        String GCM_TOKEN = "gcmtoken";
        String USER_ID = "userid";
    }

    public interface BundleKeys{
        String ATTENDEE_DETAIL = "attendeedetail";
        String EVENT_ID = "eventid";
        String EVENT_DETAIL = "eventdetail";
        String LOGIN_PHONE = "loginphone";
        String LOGIN_COUNTRY_CODE = "logincountrycode";
        String LOGIN_REGISTER_STATUS = "isregister";
        String HOME_CALENDAR_ID = "homecalid";

    }

    public interface GcmTopics{
        String MAIN = "/topics/my_little_topic";
    }

    public interface IntentFlags{
        String HOME_FLAG = "com.phdlabs.sungwon.heyoo.structure.aahome";
    }

    public static int getColor(String color){
        switch (color){
            case "butterscotch":
                return Color.parseColor("#ffb33d");
            case "seaFoamBlue":
                return Color.parseColor("#51bfcc");
            case "seaFoamGreen":
                return Color.parseColor("#68d2aa");
            case "paleRed":
                return Color.parseColor("#df4442");
            case "amethyst":
                return Color.parseColor("#925bbd");
            case "dimYellow":
                return Color.parseColor("#f0e757");
            case "darkPink":
                return Color.parseColor("#f66993");
            default:
                return Color.parseColor("#ffb33d");
        }

    }

    public interface REQUEST_CODE {
        int REQUEST_IMAGE_CAPTURE = 1;
        int CAPTURE_IMAGE = 0;
        int GALLERY_IMAGE = 1;
        int CAMERA_PERMISSION = 2;
        int WRITE_EXTERNAL_PERMISSION = 3;
    }
}
