package com.phdlabs.sungwon.heyoo.utility;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SungWon on 7/24/2017.
 */

public class BitmapUtils {

    private static final String TAG = BitmapUtils.class.getSimpleName();

    // get sd card path of image
    public static String getPath(Context context, Intent intent, Uri mCapturedImageURI) {
        Cursor cursor = null;
        String fileImagePath = null;
        try {
            Uri selectedImage;
            if (intent == null) {
                selectedImage = mCapturedImageURI;
            } else {
                if (intent.getData() == null) {
                    selectedImage = mCapturedImageURI;
                } else {
                    selectedImage = intent.getData();
                }
            }
            String[] filePathColumn = {MediaStore.MediaColumns.DATA};
            cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                fileImagePath = cursor.getString(columnIndex);
                cursor.close();
            }
        } catch (Exception ignored) {

        } finally {
            if (cursor != null)
                cursor.close();
        }
        return fileImagePath;
    }


    public static Bitmap imageOrientationValidator(Bitmap bitmap, String path) {

        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    private static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap bitmap = null;
        int w;
        int h;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            if (source == null) {
                w = 1260;
                h = 1920;
            } else {
                w = source.getWidth();
                h = source.getHeight();
            }
            bitmap = Bitmap.createBitmap(source, 0, 0, w, h, matrix, true);
        } catch (OutOfMemoryError err) {
            Log.i(TAG, "out of memory");
        }
        return bitmap;
    }


    public static Uri onOpenGallery(Activity context) {
        Uri mCapturedImageURI = null;
        if (Environment.getExternalStorageState().equals("mounted")) {

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.TITLE, context.getPackageName());
            mCapturedImageURI = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageIntent.setType("image/*");
            //setCropImage(pickImageIntent, mCapturedImageURI);
            pickImageIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            context.startActivityForResult(pickImageIntent, Constants.REQUEST_CODE.GALLARY_IMAGE);
        }

        return mCapturedImageURI;
    }


    private static void setCropImage(Intent intent, Uri mCapturedImageURI) {
        intent.putExtra("crop", "true");
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    }

    public static Bitmap getScaledBitmap(Bitmap myBitmap) {
        int height = myBitmap.getHeight();
        int width = myBitmap.getWidth();
        if (height > 2048) {
            width = width * 2048 / height;
            height = 2048;
        }
        if (width > 2048) {
            height = height * 2048 / width;
            width = 2048;
        }
        if (width == 2048 || height == 2048) {
            return Bitmap.createScaledBitmap(myBitmap, width, height, false);
        }
        return myBitmap;
    }


    public static File saveBitmap(Bitmap bitmap) {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/fieldLink";
        File dir = new File(file_path);
        if (!dir.exists()) {
            dir.delete();
            dir.mkdirs();
        }
        String format = new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault()).format(new Date());
        File file = new File(dir, format + ".jpeg");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Log.d("BitmapUtils : ", e.getMessage());
        }

        return file;
    }
}
