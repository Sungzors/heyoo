package com.phdlabs.sungwon.heyoo.utility;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SungWon on 4/26/2017.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    ViewMap views;

    public BaseViewHolder(View itemView) {
        super(itemView);
        views = new ViewMap(itemView);
        putViewsIntoMap(views);
        addClicks(views);
    }

    public BaseViewHolder(@LayoutRes int layout, @NonNull LayoutInflater inflater, ViewGroup parent) {
        this(inflater.inflate(layout, parent, false));
    }

    protected void addClicks(ViewMap views) {

    }

    protected void putViewsIntoMap(ViewMap views) {

    }

    @SuppressWarnings("unchecked")
    public <T extends View> T get(int viewId) {
        return (T) views.getView(viewId);
    }
}
