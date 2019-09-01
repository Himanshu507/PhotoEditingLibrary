package com.himanshu.editlibrary.Render;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.himanshu.editlibrary.EditLib.PhotoEditorView;
import com.himanshu.editlibrary.R;

public class RenderActivity extends AppCompatActivity {

    ImageView imageView;
    private ImageView mPhotoEditorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render);

        initViews();

        Glide.with(this).asGif().load(R.drawable.skel).into(imageView);

        //mPhotoEditorView.addView();


    }

    private void initViews() {
        imageView = findViewById(R.id.gif);
        mPhotoEditorView = findViewById(R.id.photoEditorView);
    }
}
