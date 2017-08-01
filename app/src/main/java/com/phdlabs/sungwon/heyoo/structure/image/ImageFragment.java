package com.phdlabs.sungwon.heyoo.structure.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.phdlabs.sungwon.heyoo.utility.CameraControl;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;



public class ImageFragment extends BaseFragment<ImageContract.Controller>
        implements ImageContract.View, View.OnClickListener{

    private final String TAG = "ImageFragment";

    private ImageView mImage;
    private Button mAddButton;
    private Button mDiscardButton;
    private Uri outputFileUri;
    private Uri mCapturedImageURI;
    private File chosenFile;
    private String selectedImagePath;
    private InputStream mProfilePictureInputStream;
    private Bitmap bitmap;

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
        (getBaseActivity()).getToolbar().getMenu().clear();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImage = findById(R.id.fi_image);
        mAddButton = findById(R.id.fi_add_button);
        mDiscardButton = findById(R.id.fi_discard_button);
        mImage.setOnClickListener(this);
        mAddButton.setOnClickListener(this);
        mDiscardButton.setOnClickListener(this);
        mEvent = (HeyooEvent)getArguments().getSerializable(Constants.BundleKeys.EVENT_DETAIL);
        mEndpoint = Rest.getInstance().getHeyooEndpoint();
        mEventBus = EventsManager.getInstance().getDataEventBus();
        mToken = new Preferences(getContext()).getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, null);
//        ImagePicker.startImagePicker(this, "Select Image");
//        dispatchTakePictureIntent();
        CameraControl.chooseProfilePicture(getBaseActivity());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fi_image:
                CameraControl.chooseProfilePicture(getBaseActivity());
                break;
            case R.id.fi_add_button:
                byte[] buff = new byte[8000];
                int bytesRead = 0;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    while((bytesRead = mProfilePictureInputStream.read(buff)) != -1){
                        bos.write(buff, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] byteArray = bos.toByteArray();
                RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("name", "heyoo" + System.currentTimeMillis())
                        .addFormDataPart("file", "heyoo" + System.currentTimeMillis(), RequestBody.create(MediaType.parse("image/png"),byteArray))
                        .build();


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
                            getBaseActivity().onBackPressed();
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
                            getBaseActivity().onBackPressed();
                        }
                    });
                }
                break;
            case R.id.fi_discard_button:
                getBaseActivity().onBackPressed();
                break;
        }
    }

//    private void openDialogSource(){
//        final Dialog privacyDialog = new Dialog(getBaseActivity());
//        privacyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        privacyDialog.setContentView(R.layout.dialog_camera_photo);
//        TextView camera = (TextView) privacyDialog.findViewById(R.id.tv_camera);
//        TextView gallery = (TextView) privacyDialog.findViewById(R.id.tv_gallery);
//        final DialogSelectListener listener = new DialogSelectListener() {
//            @Override
//            public void onClick(boolean isCamera) {
//                if (Build.VERSION.SDK_INT >= M) {
//                    if (isCamera)
//                        checkCameraPermission();
//                    else
//                        checkGalleryPermission();
//                } else {
//                    if (isCamera) {
//                        openCamera();
//                    } else
//                        mCapturedImageURI = BitmapUtils.onOpenGallery(ImageFragment.this);
//                }
//            }
//        };
//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onClick(true);
//                privacyDialog.dismiss();
//            }
//        });
//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onClick(false);
//                privacyDialog.dismiss();
//            }
//        });
//        privacyDialog.show();
//    }
//
//    private void dispatchTakePictureIntent() {
////        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        if (takePictureIntent.resolveActivity(getBaseActivity().getPackageManager()) != null) {
////            startActivityForResult(takePictureIntent, Constants.REQUEST_CODE.REQUEST_IMAGE_CAPTURE);
////        }
//        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
//        root.mkdirs();
//        final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
//        final File sdImageMainDirectory = new File(root, fname);
//        outputFileUri = Uri.fromFile(sdImageMainDirectory);
//
//        final List<Intent> cameraIntents = new ArrayList<Intent>();
//        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        final PackageManager packageManager = getBaseActivity().getPackageManager();
//        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
//        for(ResolveInfo res : listCam) {
//            final String packageName = res.activityInfo.packageName;
//            final Intent intent = new Intent(captureIntent);
//            intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
//            intent.setPackage(packageName);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//            cameraIntents.add(intent);
//        }
//
//
//        Intent imageIntent = new Intent();
//        imageIntent.setType("image/*");
//        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
//        Intent chooserIntent = Intent.createChooser(imageIntent, "Select Source");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
//        startActivityForResult(chooserIntent, Constants.REQUEST_CODE.REQUEST_IMAGE_CAPTURE);
//    }
//
//    public void checkCameraPermission(Context context) {
//        String[] whatPermission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        if (ContextCompat.checkSelfPermission(getBaseActivity(), whatPermission[0]) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getBaseActivity(),
//                    whatPermission,
//                    Constants.REQUEST_CODE.CAMERA_PERMISSION);
//        } else {
//            openCamera();
//        }
//    }
//
//    private void checkGalleryPermission() {
//        String whatPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
//        if (ContextCompat.checkSelfPermission(getBaseActivity(), whatPermission) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getBaseActivity(),
//                    new String[]{whatPermission},
//                    Constants.REQUEST_CODE.WRITE_EXTERNAL_PERMISSION);
//        } else {
//            outputFileUri = BitmapUtils.onOpenGallery(this);
//        }
//    }
//
//    private void openCamera() {
//        selectedImagePath = Environment.getExternalStorageDirectory() + "/heyoo" + Calendar.getInstance().getTimeInMillis() + ".jpeg";
//        File file = new File(selectedImagePath);
//        Uri outputFileUri = FileProvider.getUriForFile(getBaseActivity(), getBaseActivity().getApplicationContext().getPackageName() + ".provider", file);
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//        startActivityForResult(intent, Constants.REQUEST_CODE.CAPTURE_IMAGE);
//    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        mCapturedImageURI = CameraControl.getPictureUri(getContext(), requestCode, resultCode, data);
        if (mCapturedImageURI != null) {
            selectedImagePath = mCapturedImageURI.toString();
            //Get temporary Input Stream
            mProfilePictureInputStream = CameraControl.
                    getInputStreamFromResult(getContext(), requestCode, resultCode, data);


            //Dev
            Log.i(TAG, "onActivityResult: PICTURE URI: " + mCapturedImageURI);
            Picasso.with(getContext()).load(mCapturedImageURI).placeholder(R.drawable.pandapic).into(mImage);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
