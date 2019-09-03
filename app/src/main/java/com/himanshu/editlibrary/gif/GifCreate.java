package com.himanshu.editlibrary.gif;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.StandardGifDecoder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.himanshu.editlibrary.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TooManyListenersException;

public class GifCreate extends AppCompatActivity {

    ArrayList<Bitmap> bitmaps;
    Button button;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_create);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savegif();
            }
        });

        bitmaps = new ArrayList<>();
        Glide.with(this)
                .asGif()
                .load(R.drawable.skel)
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        try {
                            Object GifState = resource.getConstantState();
                            Field frameLoader = GifState.getClass().getDeclaredField("frameLoader");
                            frameLoader.setAccessible(true);
                            Object gifFrameLoader = frameLoader.get(GifState);

                            Field gifDecoder = gifFrameLoader.getClass().getDeclaredField("gifDecoder");
                            gifDecoder.setAccessible(true);
                            StandardGifDecoder standardGifDecoder = (StandardGifDecoder) gifDecoder.get(gifFrameLoader);
                            for (int i = 0; i < standardGifDecoder.getFrameCount(); i++) {
                                standardGifDecoder.advance();
                                bitmaps.add(standardGifDecoder.getNextFrame());
                            }
                            Toast.makeText(getApplicationContext(),"Get All Frames",Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                });


    }

    //generate gif

    public byte[] generateGIF() {
        //ArrayList<Bitmap> bitmaps = adapter.getBitmapArray();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(bos);
        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
        }
        encoder.finish();
        return bos.toByteArray();
    }

    public void savegif(){
        FileOutputStream outStream = null;
        try{
            outStream = new FileOutputStream(Environment.getExternalStorageDirectory()
                    + File.separator + ""
                    + System.currentTimeMillis()+"test.gif");
            outStream.write(generateGIF());
            outStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
