package com.phdlabs.sungwon.heyoo.utility;

import android.content.Intent;

/**
 * Created by SungWon on 6/26/2017.
 */

public class ImageUploader {



    public ImageUploader() {
    }

    public Intent imagePicker(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }


}
