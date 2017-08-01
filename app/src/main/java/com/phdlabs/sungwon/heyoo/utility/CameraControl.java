package com.phdlabs.sungwon.heyoo.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.phdlabs.sungwon.heyoo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SungWon on 7/31/2017.
 */

public final class CameraControl {

    /*CONSTANTS*/

    //Request Code
    public static final int PROFILE_PICTURE_REQUEST_CODE = 100;
    //Temporary Image File Name
    private static final String TEMPORARY_IMAGE_NAME = "temporaryImageName";
    //Image Quality
    public static final int DEFAULT_WIDTH = 1200;
    public static final int DEFAULT_HEIGHT = 1800;
    public static int minWidth = DEFAULT_WIDTH;
    public static int minHeight = DEFAULT_HEIGHT;

    private Activity mActivity;
    //TAG
    private static final String TAG = "CameraControl";

    private CameraControl() {
    }

    /**
     * This class launches an Alert Dialog to choose how to import a profile picture
     *
     * @param activity which will launch the alert dialog
     */
    public static void chooseProfilePicture(Activity activity) {
        String title = activity.getString(R.string.chooser_title);
        chooseProfilePicture(activity, title);
    }

    /**
     * This class launches an Alert Dialog to choose how to import a profile picture
     *
     * @param activity which will launch the alert dialog
     * @param title    for the alert dialog
     */
    public static void chooseProfilePicture(Activity activity, String title) {
        Intent intent = getProfilePictureIntent(activity, title);
        activity.startActivityForResult(intent, PROFILE_PICTURE_REQUEST_CODE);
    }

    /**
     * Intent used to get camera and gallery options to pick a profile picture
     *
     * @param context
     * @param title   to appear on the alert dialog
     * @return intent launcher
     */
    public static Intent getProfilePictureIntent(Context context, String title) {

        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();

        //Add Gallery Intent
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentList = addIntent(context, intentList, galleryIntent);

        /*Check camera permission before adding intent or if the user has granted camera access*/

        if (!validateProfilePicturePermissions(context, android.Manifest.permission.CAMERA) ||
                hasCameraAccess(context)) {
            //Add Image Intent
            Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camIntent.putExtra("return-data", true);
            camIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTemporaryFile(context)));
            intentList = addIntent(context, intentList, camIntent);
        }
        //If intent list has valid intents to display
        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1), title);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    intentList.toArray(new Parcelable[intentList.size()]));
        }
        return chooserIntent;
    }


    /**
     * Intent list used to display available options for a profile picture to the user
     *
     * @param context
     * @param intentList that will be returned with the targeted intents
     * @param intent     intent to be set with the package name for display
     * @return intent list with targeted intents and each listed with the package name for display
     */
    private static List<Intent> addIntent(Context context, List<Intent> intentList, Intent intent) {
        //Package Manager
        List<ResolveInfo> resInf = context.getPackageManager().queryIntentActivities(intent, 0);
        //Add available intent after query
        for (ResolveInfo resolveInfo : resInf) {
            //Gathered Information
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetIntent = new Intent(intent);
            targetIntent.setPackage(packageName);
            //Add to list
            intentList.add(targetIntent);
        }
        return intentList;
    }

    /**
     * Check for app permissions related to camera
     *
     * @param context
     * @param permission
     */
    private static boolean validateProfilePicturePermissions(Context context, String permission) {
        //Get package manager
        PackageManager packageManager = context.getPackageManager();
        try {

            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_PERMISSIONS);
            String[] requestedPermissions = null;
            //If permissions are available
            if (packageInfo != null) {
                requestedPermissions = packageInfo.requestedPermissions;
            } else {
                //packageinfo == null
                return false;
            }
            //Check requested permissions
            if (requestedPermissions.length > 0) {
                List<String> permissionsList = Arrays.asList(requestedPermissions);
                boolean istrue = permissionsList.contains(permission);
                return istrue;
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Validate current camera access given by the user
     *
     * @param context
     * @return boolean value indicating current camera access permission provided by the user
     */
    private static boolean hasCameraAccess(Context context) {
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Get image from activity result
     *
     * @param context     activity where profile picture is requested
     * @param requestCode from the activity result
     * @param resultCode  from the activity result
     * @param intent      to handle - get data for the image
     * @return File Input Stream where the image is allocated
     */
    public static InputStream getInputStreamFromResult(Context context, int requestCode,
                                                       int resultCode, Intent intent) {
        //Bitmap to be returned
        Bitmap bm = null;
        //Check request codes
        if (resultCode == Activity.RESULT_OK && requestCode == PROFILE_PICTURE_REQUEST_CODE) {
            File imageFile = getTemporaryFile(context);
            Uri selectedImage;
            boolean isCamera = intent == null ||
                    intent.getData() == null ||
                    intent.getData().toString().contains(imageFile.toString());
            if (isCamera) { //Camera Selected
                selectedImage = Uri.fromFile(imageFile);
            } else { //Gallery Selected
                selectedImage = intent.getData();
            }

            //Retrieve input stream (profile picture)
            try {
                if (isCamera) {
                    //Open temporary file stream and return it
                    return new FileInputStream(imageFile);
                } else {
                    //Or use the content resolver
                    return context.getContentResolver().openInputStream(selectedImage);
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, "getImageFromActivityResult: Could not open input stream for: " +
                        selectedImage);
                return null;
            }
        }
        return null;
    }

    /**
     * Get Filepath from Profile Image
     * @param context activity where profile picture is requested
     * @param requestCode from the activity result
     * @param resultCode from the activity result
     * @param intent holding the image uri if it's selected from the gallery
     * @return URI that points to the profile image location in album or camera
     * */
    public static Uri getPictureUri(Context context, int requestCode, int resultCode, Intent intent){
        //Check request codes
        if (resultCode ==  Activity.RESULT_OK && requestCode == PROFILE_PICTURE_REQUEST_CODE){
            File imageFile = getTemporaryFile(context);
            boolean isCamera = intent == null ||
                    intent.getData() == null ||
                    intent.getData().toString().contains(imageFile.toString());
            if (isCamera) { //Camera Selected
                return Uri.fromFile(imageFile);
            } else { //Gallery Selected
                return intent.getData();
            }
        }
        return null;
    }

    /**
     * Called after choosing the image inside an activity result to get the image
     *
     * @param context       is the activity in which the function is called (Shows profile picture)
     * @param requestCode   is the request code used to ask for the profile picture
     * @param resultCode    is the result code used to ask for the profile picture
     * @param imageReturned the intent of the activity result that contains the image path
     * @return image in bitmap format
     */

    public static Bitmap getProfileImageFromResult(Context context, int requestCode, int resultCode,
                                                   Intent imageReturned) {
        //Dev
        Log.i(TAG, "getProfileImageFromResult: called with: Request Code: " + requestCode + ", And" +
                "Result Code: " + resultCode);
        //Bitmap to be returned
        Bitmap bm = null;
        //Check Result and Request code
        if (resultCode == Activity.RESULT_OK && requestCode == PROFILE_PICTURE_REQUEST_CODE) {
            //Image File
            File imageFile = getTemporaryFile(context);
            Uri selectedProfilePicture;
            //Boolean value to be checked
            boolean isCamera = (imageReturned == null
                    || imageReturned.getData() == null
                    || imageReturned.getData().toString().contains(imageFile.toString()));
            //Check if it's from the camera or album
            if (isCamera) { /*Camera*/
                selectedProfilePicture = Uri.fromFile(imageFile);
            } else { /*Album*/
                selectedProfilePicture = imageReturned.getData();
            }
            //Dev
            Log.i(TAG, "getProfileImageFromResult: " + "SELECTED IMAGE: " + selectedProfilePicture);

            //Create Bitmap with image
            bm = decodeBitmap(context, selectedProfilePicture);
        }
        return bm;
    }

    /**
     * Loads a bitmap and avoids using too much memory loading big images (e.g.: 2560*1920)
     * Bitmap Decoding
     */

    public static Bitmap decodeBitmap(Context context, Uri theUri) {
        Bitmap outputBitmap = null;
        AssetFileDescriptor fileDescriptor = null;

        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(theUri, "r");

            // Get size of bitmap file
            BitmapFactory.Options boundsOptions = new BitmapFactory.Options();
            boundsOptions.inJustDecodeBounds = true;
            if (fileDescriptor != null) {
                BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, boundsOptions);
            }
            // Get desired sample size. Note that these must be powers-of-two.
            int[] sampleSizes = new int[]{8, 4, 2, 1};
            int targetWidth;
            int targetHeight;

            int i = 0;
            do {
                targetWidth = boundsOptions.outWidth / sampleSizes[i];
                targetHeight = boundsOptions.outHeight / sampleSizes[i];
                i++;
            }
            while (i < sampleSizes.length && (targetWidth < minWidth || targetHeight < minHeight));

            // Decode bitmap at desired size
            BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
            decodeOptions.inSampleSize = sampleSizes[i];
            if (fileDescriptor != null) {
                outputBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, decodeOptions);
            }
            if (outputBitmap != null) {
                Log.i(TAG, "Loaded image with sample size " + decodeOptions.inSampleSize + "\t\t"
                        + "Bitmap width: " + outputBitmap.getWidth()
                        + "\theight: " + outputBitmap.getHeight());
            }
            if (fileDescriptor != null) {
                fileDescriptor.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputBitmap;
    }

    /**
     * Temporary Image File for Cache, uses temporary image name constant
     *
     * @param context
     * @return Temporary File to store the profile picture
     */
    public static File getTemporaryFile(Context context) {
        return new File(context.getExternalCacheDir(), TEMPORARY_IMAGE_NAME);
    }

//    /*
//     * Image Quality
//     */
//
//    public static void setMinQuality(int minWidth, int minHeight){
//
//        CameraControl.minWidth = minWidth;
//        CameraControl.minHeight = minHeight;
//
//    }

}

//public class CameraControl {
//
//
//    //TAG
//    private static final String TAG = "CameraControl";
//
//    //Camera Result
//    public static final int PROFILE_PICTURE = 0x1000;
//
//    /*Intent Query for camera & gallery*/
//    public void profilePictureIntent(Activity context) {
//
//        /*Choose to take a picture with the camera*/
//        //Intent camIntent = new Intent("android.media.action.IMAGE_CAPTURE"); //More options
//        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        /*Choose to get an image from the gallery*/
//        //Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT); //More options
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        galleryIntent.setType("image/*");
//
//        //Available actions
//        List<ResolveInfo> info = new ArrayList<ResolveInfo>();
//        //Intent list
//        List<Intent> yourIntentsList = new ArrayList<Intent>();
//        PackageManager packageManager = context.getPackageManager();
//
//        /*Query camera options ordered from best to worst*/
//        List<ResolveInfo> listCam = packageManager.queryIntentActivities(camIntent, 0);
//
//        for (ResolveInfo res : listCam) {
//
//            //Dev
//            Log.i(TAG, "profilePictureIntent: Camera Components: " + res.activityInfo.packageName);
//            Log.i(TAG, "profilePictureIntent: Camera Components: NAME::  " + res.activityInfo.name);
//
//            final Intent finalIntent = new Intent(camIntent);
//            finalIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            yourIntentsList.add(finalIntent);
//            info.add(res);
//        }
//
//        /*Query gallery options ordered from best to worst*/
//        List<ResolveInfo> listGall = packageManager.queryIntentActivities(galleryIntent, 0);
//
//        for (ResolveInfo res : listGall) {
//
//            //Dev
//            Log.i(TAG, "profilePictureIntent: Gallery Components:  " + res.activityInfo.packageName);
//            Log.i(TAG, "profilePictureIntent: Gallery Components: NAME: " + res.activityInfo.name);
//
//            final Intent finalIntent = new Intent(galleryIntent);
//            finalIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            yourIntentsList.add(finalIntent);
//            info.add(res);
//        }
//
//        //Multiple options alert dialog
//        multipleOptionsAlertDialog(context, yourIntentsList, info);
//    }
//
//    private void multipleOptionsAlertDialog(final Activity context, final List<Intent> intents,
//                                            List<ResolveInfo> activitiesInfo) {
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setTitle("Navigate to your Profile Photo !");
//        dialog.setAdapter(buildAdapter(context, activitiesInfo),
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = intents.get(id);
//                        context.startActivityForResult(intent, PROFILE_PICTURE);
//                    }
//                });
//
//        dialog.setNeutralButton("Cancel",
//                new android.content.DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        dialog.show();
//
//    }
//
//    /**
//     * Build the list of items to show using the intent_listview_row layout.
//     *
//     * @param context
//     * @param activitiesInfo
//     * @return
//     */
//    private static ArrayAdapter<ResolveInfo> buildAdapter(final Context context, final List<ResolveInfo> activitiesInfo) {
//        return new ArrayAdapter<ResolveInfo>(context, R.layout.alert_dialog_list_row, R.id.TextView_AlertDialog_Row, activitiesInfo) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                ResolveInfo res = activitiesInfo.get(position);
//                ImageView image = (ImageView) view.findViewById(R.id.ImageView_AlertDialog_Row);
//                image.setImageDrawable(res.loadIcon(context.getPackageManager()));
//                return view;
//            }
//        };
//    }
//
//}
