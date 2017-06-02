package com.phdlabs.sungwon.heyoo.structure.aahome.eventedit;

import android.content.Context;
import android.widget.Toast;

import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;
import com.phdlabs.sungwon.heyoo.model.HeyooMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/30/2017.
 */

public class EventEditController implements EventEditContract.Controller {

    private static final String TAG = "EventEditController";

    private EventEditContract.View mView;
    private HeyooEventManager mEventManager;

    EventEditController(EventEditContract.View mView){
        this.mView = mView;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onMediaAddClicked() {
        Toast.makeText(getContext(), "Media Add Button Clicked (feature to come later)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveDraftClicked() {
        mView.saveEvent();
    }

    @Override
    public void onPublishClicked() {
        mView.postEvent();
    }

    @Override
    public List<HeyooMedia> getAssociatedMedia() {
        List<HeyooMedia> list = getDummyMedia();
        List<HeyooMedia> asslist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getEvent_id() == mView.getEventid()){
                asslist.add(list.get(i));
            }
        }
        return asslist;
    }

    public List<HeyooMedia> getDummyMedia(){
        List<HeyooMedia> list = new ArrayList<>();
        HeyooMedia media1 = new HeyooMedia("Dummy Media", "dummy", "http://i.imgur.com/T7N4qg6.jpg", mView.getEventid());
        HeyooMedia media2 = new HeyooMedia("Dummy Media 2", "dummy2", "http://i.imgur.com/nMRJBTj.jpg", mView.getEventid());
        HeyooMedia media3 = new HeyooMedia("Dummy Media 3", "dummy3", "http://i.imgur.com/HfWzk0i.jpg", mView.getEventid());
        HeyooMedia media4 = new HeyooMedia("Dummy Media 4", "dummy4", "http://i.imgur.com/pPVBUld.jpg", mView.getEventid());
        HeyooMedia media5 = new HeyooMedia("Dummy Media 5", "dummy5", "http://i.imgur.com/SMUFKgO.jpg", mView.getEventid());
        list.add(media1);
        list.add(media2);
        list.add(media3);
        list.add(media4);
        list.add(media5);
        return list;
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public int getEventid() {
        return mView.getEventid();
    }

    @Override
    public void onStop() {

    }
}
