package com.phdlabs.sungwon.heyoo.utility;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.phdlabs.sungwon.heyoo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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

    public ImageExpander(Context context, String imagelist) {
        this.mContext = context;
        this.mImageList.add(imagelist);
        mViewList = new ArrayList<>();
    }

    public ImageExpander(Context context){
        this.mContext = context;
        mViewList = new ArrayList<>();
    }

    public void displayImage(String url, ImageView view){
        Picasso.with(mContext)
                .load(url)
                .into(view);
    }

    public void displayImage(int x, int y, String url, ImageView view){
        Picasso.with(mContext)
                .load(url)
                .resize(x, y)
                .centerCrop()
                .into(view);
    }

    public void displayRoundedImage(String url, ImageView view){
        Picasso.with(mContext)
                .load(url)
                .transform(new RoundedCornersTransformation(3, 0))
                .placeholder(R.drawable.pandapic)
                .into(view);
    }

    /**
     * Method to create a gradually expanding image
     * @param container Container which the images will be inputted
     * @return List of ImageViews added
     */
    public List<ImageView> insertExpandingImage(View container){
        int sizex1Diff = 2;
        int sizeyDiff = 2;
        int sizex2Diff = 0;
        LinearLayout picLayout = new LinearLayout(mContext);
        picLayout.setPadding(0,0,0,0);
        LinearLayout.LayoutParams picLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2);
        picLayoutParam.setMargins(0,0,0,0);
        picLayoutParam.setLayoutDirection(LinearLayout.HORIZONTAL);
        picLayout.setLayoutParams(picLayoutParam);
        LinearLayout picLayout2 = new LinearLayout(mContext);
        if(mImageList.size()>2){
            LinearLayout.LayoutParams picLayoutParam2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            picLayoutParam2.setMargins(0,0,0,0);
            picLayout2.setPadding(0,0,0,0);
            picLayoutParam2.setLayoutDirection(LinearLayout.HORIZONTAL);
            picLayout2.setLayoutParams(picLayoutParam2);
            sizeyDiff = 3;
            sizex2Diff = mImageList.size() - 2;
        } else if(mImageList.size()==1){
            sizex1Diff = 1;
        }
        for (int i = 0; i < mImageList.size(); i++) {
            final ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams iVParam2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            imageView.setLayoutParams(iVParam2);
            if(i < 2) {
                picLayout.addView(imageView);
                displayImage(360/sizex1Diff - 6, 600/sizeyDiff, mImageList.get(i), imageView);
                imageView.generateViewId();
                mViewList.add(imageView);
            } else if (i < 7){
                displayImage(360/sizex2Diff - 6, 300/sizeyDiff, mImageList.get(i), imageView);
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
