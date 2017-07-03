package com.phdlabs.sungwon.heyoo.structure.image;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.event.EventMediaPostEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.response.EventMediaPostResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;
import com.phdlabs.sungwon.heyoo.model.HeyooEvent;
import com.phdlabs.sungwon.heyoo.model.HeyooEventManager;
import com.phdlabs.sungwon.heyoo.structure.aahome.HomeActivity;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Files;
import com.phdlabs.sungwon.heyoo.utility.ImagePicker;
import com.phdlabs.sungwon.heyoo.utility.Preferences;
import com.phdlabs.sungwon.heyoo.utility.Procedure;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by SungWon on 6/26/2017.
 */

public class ImageFragment extends BaseFragment<ImageContract.Controller>
        implements ImageContract.View, View.OnClickListener{

    private ImageView mImage;
    private Button mAddButton;
    private Button mDiscardButton;
    private Uri outputFileUri;
    private File chosenFile;

    private HeyooEvent mEvent;

    private HeyooEndpoint mEndpoint;
    private String mToken;
    private EventBus mEventBus;

    private boolean isNull = false;

    private final int YOUR_SELECT_PICTURE_REQUEST_CODE = 3636;

    public static ImageFragment newInstance(HeyooEvent event){
        Bundle args = new Bundle();
        args.putSerializable(Constants.BundleKeys.EVENT_DETAIL, event);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    protected ImageContract.Controller createController() {
        return new ImageController(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_image;
    }

    @Override
    public void onStart() {
        super.onStart();
        getBaseActivity().setToolbarTitle("Adding Image");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImage = findById(R.id.fi_image);
        mAddButton = findById(R.id.fi_add_button);
        mDiscardButton = findById(R.id.fi_discard_button);
        mAddButton.setOnClickListener(this);
        mDiscardButton.setOnClickListener(this);
        mEvent = (HeyooEvent)getArguments().getSerializable(Constants.BundleKeys.EVENT_DETAIL);
        mEndpoint = Rest.getInstance().getHeyooEndpoint();
        mEventBus = EventsManager.getInstance().getDataEventBus();
        mToken = new Preferences(getContext()).getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null);
        ImagePicker.startImagePicker(this, "Select Image");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fi_add_button:
//                mEvent.addMedia(outputFileUri.getPath());
//                File file = new File(Environment.getExternalStorageDirectory(), "/My device/MyDir/img_1498624680067.jpg");
//                RequestBody formBody = RequestBody.create(MediaType.parse("image/*"), file);
                RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("name", chosenFile.getPath())
                        .addFormDataPart("file", chosenFile.getPath(), RequestBody.create(MediaType.parse("image/png"),chosenFile)).build();


                showProgress();
                if(mEvent.getId() == -1){
                    Call<EventMediaPostResponse> call = mEndpoint.postEmptyMedia(mToken, formBody);
                    call.enqueue(new HCallback<EventMediaPostResponse, EventMediaPostEvent>(mEventBus) {
                        @Override
                        protected void onSuccess(EventMediaPostResponse data) {
                            hideProgress();
                            mEvent.addMedia(data.getMedium().getFile_path());
                            mEvent.setId(data.getMedium().getEvent_id());
                            ((HomeActivity)getBaseActivity()).setEvent(mEvent);
                            ((HomeActivity)getBaseActivity()).setImageEventID(mEvent.getId());
                            mEventBus.post(new EventMediaPostEvent());
                            onBackPressed();
                        }
                    });
                } else {
                    Call<EventMediaPostResponse> call = mEndpoint.postEventMedia(mEvent.getId(), mToken, formBody);
                    call.enqueue(new HCallback<EventMediaPostResponse, EventMediaPostEvent>(mEventBus) {
                        @Override
                        protected void onSuccess(EventMediaPostResponse data) {
                            hideProgress();
                            HeyooEventManager manager = HeyooEventManager.getInstance();
                            mEvent.addMedia(data.getMedium().getFile_path());
                            manager.setEvents(mEvent.getId(), mEvent);
                            mEventBus.post(new EventMediaPostEvent());
                            onBackPressed();
                        }
                    });
                }
                break;
            case R.id.fi_discard_button:
                onBackPressed();
                break;
        }
    }

    private void openImageIntent() {

        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getBaseActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
//                final boolean isCamera;
//                if (data == null) {
//                    isCamera = true;
//                } else {
//                    final String action = data.getAction();
//                    if (action == null) {
//                        isCamera = false;
//                    } else {
//                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    }
//                }
//
//                Uri selectedImageUri;
//                if (isCamera) {
//                    selectedImageUri = outputFileUri;
//                } else {
//                    selectedImageUri = data == null ? null : data.getData();
//                }
//                Drawable selectedImage;
//                try {
//                    InputStream inputStream = getBaseActivity().getContentResolver().openInputStream(selectedImageUri);
//                    selectedImage = Drawable.createFromStream(inputStream, selectedImageUri.toString() );
////                    outputFileUri = selectedImageUri;
//                    mImage.setBackground(selectedImage);
//                } catch (FileNotFoundException e) {
//                    Toast.makeText(getContext(), "Image Cannot Be Found", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }

        ImagePicker.getImageFromResultAsync(getActivity(), requestCode, resultCode, data,
                new ImagePicker.Properties(Files.getTempFile(getContext())),
                new Procedure<ImagePicker.ImageResult>() {
                    @Override
                    public void apply(ImagePicker.ImageResult imageResult) {
//                        Bitmap bmp = BitmapFactory.decodeFile(imageResult.file.getAbsolutePath());
//                        mImage.setImageBitmap(imageResult.bitmap);
                        Picasso.with(getContext()).load(imageResult.file).into(mImage);
                        chosenFile = imageResult.file;
                    }
                },
                new Procedure<ImagePicker.ImageResult>() {
                    @Override
                    public void apply(ImagePicker.ImageResult imageResult) {
                        ImagePicker.ImageResult x = imageResult;
                        Bitmap y = imageResult.bitmap;
                        File z = imageResult.file;
                    }
                });
    }
}
