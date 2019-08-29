package com.himanshu.editlibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.himanshu.editlibrary.EditingLibrary.EditImageActivity;
import com.himanshu.editlibrary.Utils.image_temp;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView capture, swap, flash, cancel;
    CameraView cameraView;
    File file;
    boolean swapbool = false, flashbool = false;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init_Views();
        checkPermissions();
        cameraView.setLifecycleOwner(this);
        cameraView.setMode(Mode.PICTURE);
        onButtonClicks();

    }

    private void onButtonClicks() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

                        Intent intent = new Intent(getApplicationContext(), EditImageActivity.class);
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

    private void init_Views() {
        capture = findViewById(R.id.capture);
        swap = findViewById(R.id.switch_camera);
        flash = findViewById(R.id.flash);
        cancel = findViewById(R.id.cancel);
        cameraView = findViewById(R.id.camera);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }
}
