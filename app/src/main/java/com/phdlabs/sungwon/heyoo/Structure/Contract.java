package com.phdlabs.sungwon.heyoo.Structure;

import android.content.Context;

/**
 * Created by SungWon on 4/13/2017.
 */

public interface Contract {
    interface BaseView {
        Context getContext();

        void showError(String errorMessage);

//        void showProgress();
//
//        void hideProgress();

        void close();
    }

    interface BaseController {
        void onStart();

        void onResume();

        void onPause();

        void onStop();
    }

    interface BaseSearchController extends BaseController {
        void onSearchQuery(String query);
    }
}
