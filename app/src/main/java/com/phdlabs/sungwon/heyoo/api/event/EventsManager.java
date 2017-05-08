package com.phdlabs.sungwon.heyoo.api.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by SungWon on 4/25/2017.
 */

public class EventsManager {

    private static EventsManager sInstance = new EventsManager();
    private final EventBus mUiEventBus;
    private final EventBus mDataEventBus;

    public static EventsManager getInstance() {
        return sInstance;
    }

    private EventsManager() {
        mUiEventBus = new EventBus();
        mDataEventBus = new EventBus();
    }

    public EventBus getUiEventBus() {
        return mUiEventBus;
    }

    public EventBus getDataEventBus() {
        return mDataEventBus;
    }
}
