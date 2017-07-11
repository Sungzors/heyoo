package com.phdlabs.sungwon.heyoo.utility.adapter;

import android.widget.ListAdapter;

import java.util.List;

/**
 * Created by SungWon on 6/20/2017.
 */

public interface HBaseAdapter<T> extends ListAdapter{

    void setItems(List<T> rawList);
}
