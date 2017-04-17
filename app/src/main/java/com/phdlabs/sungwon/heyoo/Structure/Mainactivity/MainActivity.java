package com.phdlabs.sungwon.heyoo.Structure.Mainactivity;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.widget.Toolbar;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.Structure.BaseActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class MainActivity<Controller extends MainContract.Controller> extends BaseActivity {

    /**
     * MainActivity is an abstract class with the purpose of setting up the rules for a bottom TabLayout in the main view of the app.
     * Needs to be extended by any Activity relating to a tab, requires a Controller corresponding to Activity
     */
    public static final int TAB_HOME = 0;
    public static final int TAB_CALENDAR = 1;
    public static final int TAB_NEW = 2;
    public static final int TAB_MESSAGES = 3;
    public static final int TAB_ALERTS = 4;
    private Toolbar mToolbar;

    /**
     * Limits selectable tab to the 5 listed in IntDef
     */
    @IntDef({TAB_HOME, TAB_CALENDAR, TAB_NEW, TAB_MESSAGES, TAB_ALERTS})
    @Retention(RetentionPolicy.SOURCE)
    @interface Tab {

    }

    private Controller mController;

    /**
     * view set up
     * @return layout id
     */
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int contentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
