package com.phdlabs.sungwon.heyoo.utility;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SungWon on 5/24/2017.
 */

public class ImageExpander {

    private Context mContext;
    private List<String> mImageList;
    private List<ImageView> mViewList;

    public ImageExpander(Context context, List<String> imagelist) {
        this.mContext = context;
        this.mImageList = imagelist;
        mViewList = new ArrayList<>();
    }

    public void displayImage(String url, ImageView view){
        Picasso.with(mContext)
                .load(url)
                .into(view);
    }

    /**
     * Method to create a gradually expanding image
     * @param container Container which the images will be inputted
     * @return List of IDs for ImageViews added
     */
    public List<ImageView> insertExpandingImage(View container){
        LinearLayout picLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams picLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        picLayoutParam.setLayoutDirection(LinearLayout.HORIZONTAL);
        picLayout.setLayoutParams(picLayoutParam);
        LinearLayout picLayout2 = new LinearLayout(mContext);
        if(mImageList.size()>2){
            LinearLayout.LayoutParams picLayoutParam2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            picLayoutParam2.setLayoutDirection(LinearLayout.HORIZONTAL);
            picLayout2.setLayoutParams(picLayoutParam2);
        }
        for (int i = 0; i < mImageList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams iVParam2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            imageView.setLayoutParams(iVParam2);
            if(i < 2) {
                displayImage(mImageList.get(i), imageView);
                picLayout.addView(imageView);
                imageView.generateViewId();
                mViewList.add(imageView);
            } else if (i < 7){
                displayImage(mImageList.get(i), imageView);
                picLayout2.addView(imageView);
                imageView.generateViewId();
                mViewList.add(imageView);
            }
        }
        ((LinearLayout)container).addView(picLayout);
        if(mImageList.size()>2){
            ((LinearLayout)container).addView(picLayout2);
        }
        return mViewList;
    }

}
