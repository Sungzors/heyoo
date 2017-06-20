package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;

import java.util.List;

/**
 * Created by SungWon on 6/20/2017.
 */

public class DefaultSpinnerAdapter<T> extends BaseArrayAdapter<T>{

    private int layout;

    public DefaultSpinnerAdapter(Context context) {
        super(context);
    }

    public DefaultSpinnerAdapter(Context context, List<T> items, int layout) {
        super(context, items);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, T data, ViewGroup parent, int position) {
        return LayoutInflater.from(context).inflate(getDropDownLayoutRes(), parent, false);
    }

    @Override
    public void bindView(View view, T data, int position) {
        ((TextView) view.findViewById(R.id.text)).setText(getItemValue(data));
    }

    protected String getItemValue(T data) {
        return data.toString();
    }

    @Override
    protected int getDropDownLayoutRes() {
        return layout;
    }
}
