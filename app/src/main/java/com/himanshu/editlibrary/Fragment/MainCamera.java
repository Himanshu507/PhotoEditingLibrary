package com.himanshu.editlibrary.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.himanshu.editlibrary.EditingLibrary.EditImageActivity;
import com.himanshu.editlibrary.R;
import com.himanshu.editlibrary.Utils.image_temp;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;


public class MainCamera extends Fragment {

    ImageView capture, swap, flash, cancel;
    CameraView cameraView;
    File file;
    boolean swapbool = false, flashbool = false;


    public MainCamera() {

    }

   public static MainCamera newInstance() {
        return new MainCamera();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

   /* private void init_Views() {
        capture = findViewById(R.id.capture);
        swap = findViewById(R.id.switch_camera);
        flash = findViewById(R.id.flash);
        cancel = findViewById(R.id.cancel);
        cameraView = findViewById(R.id.camera);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_camera, container, false);
        init_Views(view);
        cameraView.setLifecycleOwner(this);
        cameraView.setMode(Mode.PICTURE);
        onButtonClicks();
        return view;
    }

    private void onButtonClicks() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroy();
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.addCameraListener(new CameraListener() {
                    @Override
                    public void onPictureTaken(PictureResult result) {
                        // A Picture was taken!
                        BitmapCallback bitmapCallback = new BitmapCallback() {
                            @Override
                            public void onBitmapReady(@Nullable Bitmap bitmap) {
                                image_temp.setPHOTO(bitmap);
                                return;
                            }
                        };

                        result.toBitmap(cameraView.getWidth(), cameraView.getHeight(), bitmapCallback);

//                        file = new File(getCacheDir(), "/" + UUID.randomUUID().toString() + ".jpg");
//                        FileCallback callback = new FileCallback() {
//                            @Override
//                            public void onFileReady(@Nullable File file) {
//                                Matrix matrix = new Matrix();
//
//                                matrix.postRotate(0);
//                                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
//                                bitmap = Bitmap.createScaledBitmap(bitmap,140,130,true);
//                                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//                                image_temp.setPHOTO(rotatedBitmap);
//                                return;
//                            }
//                        };
//                        result.toFile(file, callback);
//                        byte[] data = result.getData();
//                        CameraUtils.writeToFile(data, file, callback);

                        Intent intent = new Intent(getContext(), EditImageActivity.class);
//                        intent.putExtra("BitmapImage", file.getAbsolutePath());
                        startActivity(intent);

                    }

                });
                cameraView.takePicture();

            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flashbool) {
                    cameraView.setFlash(Flash.TORCH);
                    flash.setImageDrawable(getResources().getDrawable(R.drawable.flashon));
                    flashbool = true;
                } else {
                    flash.setImageDrawable(getResources().getDrawable(R.drawable.flashoff));
                    cameraView.setFlash(Flash.OFF);
                    flashbool = false;
                }
            }
        });

        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!swapbool) {
                    cameraView.setFacing(Facing.FRONT);
                    swapbool = true;
                } else {
                    cameraView.setFacing(Facing.BACK);
                    swapbool = false;
                }
            }
        });
    }


    private void init_Views(View view) {
        capture = view.findViewById(R.id.capture);
        swap = view.findViewById(R.id.switch_camera);
        flash = view.findViewById(R.id.flash);
        cancel = view.findViewById(R.id.cancel);
        cameraView = view.findViewById(R.id.camera);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
      //  mListener = null;
    }


}
