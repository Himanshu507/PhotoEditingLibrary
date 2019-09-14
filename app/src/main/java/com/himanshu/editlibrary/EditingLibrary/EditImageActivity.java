package com.himanshu.editlibrary.EditingLibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.himanshu.editlibrary.EditLib.OnPhotoEditorListener;
import com.himanshu.editlibrary.EditLib.PhotoEditor;
import com.himanshu.editlibrary.EditLib.PhotoEditorView;
import com.himanshu.editlibrary.EditLib.PhotoFilter;
import com.himanshu.editlibrary.EditLib.SaveSettings;
import com.himanshu.editlibrary.EditLib.TextStyleBuilder;
import com.himanshu.editlibrary.EditLib.ViewType;
import com.himanshu.editlibrary.EditingLibrary.base.BaseActivity;
import com.himanshu.editlibrary.EditingLibrary.filters.FilterListener;
import com.himanshu.editlibrary.EditingLibrary.filters.FilterViewAdapter;
import com.himanshu.editlibrary.Oil.olfilter;
import com.himanshu.editlibrary.R;
import com.himanshu.editlibrary.Utils.AndroidUtils;
import com.himanshu.editlibrary.Utils.image_temp;

import java.io.File;
import java.io.IOException;

public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener,
        View.OnClickListener,
        PropertiesBSFragment.Properties,
        StickerBSFragment.StickerListener,
        FilterListener,Space_bottom.BottomSheetListener {

    private static final String TAG = EditImageActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    private PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
    private PropertiesBSFragment mPropertiesBSFragment;
    private EmojiBSFragment mEmojiBSFragment;
    private StickerBSFragment mStickerBSFragment;
    private Typeface mWonderFont;
    private RecyclerView mRvTools, mRvFilters;
    private FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);
    private ConstraintLayout mRootView;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    private boolean mIsFilterVisible;
    private LinearLayoutCompat linearLayout, filtercheck;
    Typeface mTextRobotoTf, mEmojiTypeFace;
    Button imgSave;
    ImageView text, filter, sticker, tools, set_filter, cancel_filter, oil_filter_img, Space;
    TextView text_oil;
    View upperview, lowerview;
    Boolean upperbool = false, lowerbool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_edit_image);

        initViews();

        mWonderFont = Typeface.createFromAsset(getAssets(), "beyond_wonderland.ttf");

        mPropertiesBSFragment = new PropertiesBSFragment();
        mEmojiBSFragment = new EmojiBSFragment();
        mStickerBSFragment = new StickerBSFragment();
        mStickerBSFragment.setStickerListener(this);
        //mEmojiBSFragment.setEmojiListener(this);
        mPropertiesBSFragment.setPropertiesChangeListener(this);

       /* LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);*/

        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvFilters.setLayoutManager(llmFilters);
        mRvFilters.setAdapter(mFilterViewAdapter);
        Bitmap bitmap = null;
        bitmap = image_temp.getPHOTO();
        //image_temp.setPHOTO(null);

        mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                /*.setDefaultTextTypeface(mTextRobotoTf)
                .setDefaultEmojiTypeface(mEmojiTypeFace)*/
                .build(); // build photo editor sdk

        mPhotoEditor.setOnPhotoEditorListener(this);

        //Set Image Dynamically
        mPhotoEditorView.getSource().setImageBitmap(bitmap);
        mRvFilters.setVisibility(View.GONE);
        oil_filter_img.setVisibility(View.GONE);
        text_oil.setVisibility(View.GONE);
    }

    private void initViews() {

        upperview = findViewById(R.id.upperview);
        lowerview = findViewById(R.id.bottomView);


        Space  = findViewById(R.id.Space);
        Space.setOnClickListener(this);

        oil_filter_img = findViewById(R.id.oil_filter_img);
        oil_filter_img.setOnClickListener(this);

        text_oil = findViewById(R.id.oil_text);


        filtercheck = findViewById(R.id.filter_Check);
        mPhotoEditorView = findViewById(R.id.photoEditorView);

        mRvFilters = findViewById(R.id.rvFilterView);
        mRootView = findViewById(R.id.rootView);

        set_filter = findViewById(R.id.setFilter);
        set_filter.setOnClickListener(this);

        cancel_filter = findViewById(R.id.clearfilter);
        cancel_filter.setOnClickListener(this);

        sticker = findViewById(R.id.sticker);
        sticker.setOnClickListener(this);

        text = findViewById(R.id.text);
        text.setOnClickListener(this);

        imgSave = findViewById(R.id.imgSave);
        imgSave.setOnClickListener(this);

        tools = findViewById(R.id.Tools);
        tools.setOnClickListener(this);

        linearLayout = findViewById(R.id.linearLayout);

        filter = findViewById(R.id.filter_img);
        filter.setOnClickListener(this);

    }

    @Override
    public void onEditTextChangeListener(final View rootView, String text, int colorCode) {
        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show(this, text, colorCode);
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode, float textSize, Typeface font, int alpha_Text) {
                final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                styleBuilder.withTextColor(colorCode);
                styleBuilder.withTextFont(font);
                styleBuilder.withTextSize(textSize);
                styleBuilder.withTextAlpha(alpha_Text);
                Log.d("TAG", inputText);
                mPhotoEditor.editText(rootView, inputText, styleBuilder);
            }
        });
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgSave:
                saveImage();
                break;
            case R.id.text:
                openText();
                break;
            case R.id.sticker:
                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
                break;
            case R.id.filter_img:
                oil_filter_img.setVisibility(View.VISIBLE);
                text_oil.setVisibility(View.VISIBLE);
                filtercheck.setVisibility(View.VISIBLE);
                imgSave.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                mRvFilters.setVisibility(View.VISIBLE);
                showFilter(true);
                break;
            case R.id.Tools:
                galleryOrCameraDialog();
                break;

            case R.id.oil_filter_img:
                oilFilter(image_temp.getPHOTO());
                break;

            case R.id.clearfilter:
                mPhotoEditorView.getSource().setImageBitmap(image_temp.getPHOTO());
                filtercheck.setVisibility(View.GONE);
                mRvFilters.setVisibility(View.GONE);
                oil_filter_img.setVisibility(View.GONE);
                text_oil.setVisibility(View.GONE);
                showFilter(false);
                linearLayout.setVisibility(View.VISIBLE);
                imgSave.setVisibility(View.VISIBLE);
                break;

            case R.id.setFilter:
                onBackPressed();
                break;

            case R.id.Space:
                upperview.setVisibility(View.VISIBLE);
                Space_bottom bottomSheet = new Space_bottom();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                break;
        }
    }

    private void openText() {
        mRvFilters.setVisibility(View.GONE);
        TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode, float textSize , Typeface font, int alpha_Text) {
                final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                styleBuilder.withTextColor(colorCode);
                styleBuilder.withTextSize(textSize);
                styleBuilder.withTextAlpha(alpha_Text);
                styleBuilder.withTextFont(font);
                mPhotoEditor.addText(inputText, styleBuilder);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void saveImage() {
        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showLoading("Saving...");
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + ""
                    + System.currentTimeMillis() + ".png");
            try {
                file.createNewFile();

                SaveSettings saveSettings = new SaveSettings.Builder()
                        .setClearViewsEnabled(true)
                        .setTransparencyEnabled(true)
                        .build();

                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        hideLoading();
                        showSnackbar("Image Saved Successfully");
                        mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        hideLoading();
                        showSnackbar("Failed to save Image");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                hideLoading();
                showSnackbar(e.getMessage());
            }
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    mPhotoEditor.clearAllViews();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mPhotoEditorView.getSource().setImageBitmap(photo);
                    break;
                case PICK_REQUEST:
                    try {
                        mPhotoEditor.clearAllViews();
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setBrushColor(colorCode);
    }

    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setOpacity(opacity);
    }

    @Override
    public void onBrushSizeChanged(int brushSize) {
        mPhotoEditor.setBrushSize(brushSize);
    }

    @Override
    public void isPermissionGranted(boolean isGranted, String permission) {
        if (isGranted) {
            saveImage();
        }
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want to exit without saving image ?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveImage();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();

    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }


    void showFilter(boolean isVisible) {

        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mRootView);

        if (isVisible) {
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
        } else {
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.END);
        }

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);

        mConstraintSet.applyTo(mRootView);

    }

    @Override
    public void onBackPressed() {
        if (mIsFilterVisible) {
            filtercheck.setVisibility(View.GONE);
            mRvFilters.setVisibility(View.GONE);
            oil_filter_img.setVisibility(View.GONE);
            text_oil.setVisibility(View.GONE);
            imgSave.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        } else if (!mPhotoEditor.isCacheEmpty()) {
            linearLayout.setVisibility(View.VISIBLE);
            showSaveDialog();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
    }

    public void galleryOrCameraDialog() {
        //permissionManager.checkAndRequestPermissions(this);
        ImageView close, crop, brush, eraser, undo;
        View view = View.inflate(this, R.layout.dialog_tool, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .show();

        close = view.findViewById(R.id.close);
        crop = view.findViewById(R.id.crop);
        brush = view.findViewById(R.id.brush);
        eraser = view.findViewById(R.id.eraser);
        undo = view.findViewById(R.id.undo);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoEditor.setBrushDrawingMode(true);
                mPropertiesBSFragment.show(getSupportFragmentManager(), mPropertiesBSFragment.getTag());
                alertDialog.dismiss();
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoEditor.brushEraser();
                alertDialog.dismiss();
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoEditor.undo();
                alertDialog.dismiss();
            }
        });
    }

    public void oilFilter(final Bitmap bitmap){
        Toast.makeText(getApplicationContext(),"OilFilter clicked",Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(){
            public void run() {
                final int width = bitmap.getWidth();
                    final int height = bitmap.getHeight();
                    final int[] colors = AndroidUtils.bitmapToIntArray(bitmap);
                    final Rect rect = new Rect(0, 0, width, height);
                    final olfilter filter = new olfilter();

                    final int[] newImage = filter.filterPixels(width,height,colors,rect);
                    final Bitmap bitmapp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    // vector is your int[] of ARGB

                    bitmapp.setPixels(newImage, 0, width, 0, 0, width, height);

                    EditImageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPhotoEditorView.getSource().setImageBitmap(bitmapp);
                        }
                    });
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void set_height(int heigh) {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        ViewGroup.LayoutParams layoutParams = upperview.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = heigh;
        upperview.setLayoutParams(layoutParams);
    }

    @Override
    public void show_bottomSpace() {
        lowerview.setVisibility(View.VISIBLE);
    }
}
