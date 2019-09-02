package com.himanshu.editlibrary.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.himanshu.editlibrary.EditLib.OnPhotoEditorListener;
import com.himanshu.editlibrary.EditLib.PhotoEditor;
import com.himanshu.editlibrary.EditLib.PhotoEditorView;
import com.himanshu.editlibrary.EditLib.PhotoFilter;
import com.himanshu.editlibrary.EditLib.TextStyleBuilder;
import com.himanshu.editlibrary.EditLib.ViewType;
import com.himanshu.editlibrary.EditingLibrary.ColorPickerAdapter;
import com.himanshu.editlibrary.EditingLibrary.EmojiBSFragment;
import com.himanshu.editlibrary.EditingLibrary.PropertiesBSFragment;
import com.himanshu.editlibrary.EditingLibrary.StickerBSFragment;
import com.himanshu.editlibrary.EditingLibrary.TextEditorDialogFragment;
import com.himanshu.editlibrary.EditingLibrary.filters.FilterListener;
import com.himanshu.editlibrary.R;

public class TextFragment extends Fragment implements OnPhotoEditorListener,
        View.OnClickListener,
        PropertiesBSFragment.Properties,
        StickerBSFragment.StickerListener{

    private PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
    private ImageView background_img, text_img, sticker_img;
    private PropertiesBSFragment mPropertiesBSFragment;
    private StickerBSFragment mStickerBSFragment;
    private RecyclerView color_Recycler;
    LinearLayoutCompat linearLayoutCompat;
    ColorPickerAdapter colorPickerAdapter;
    Typeface mEmojiTypeFace;
    View viwww;
    Context context;

    public TextFragment() {
        // Required empty public constructor
    }


    public static TextFragment newInstance() {
        return new TextFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // openText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_text, container, false);
        initViews(view);
        ButtonWorks();

        context = view.getContext();

        mStickerBSFragment = new StickerBSFragment();
        mStickerBSFragment.setStickerListener(this);

        mEmojiTypeFace = Typeface.createFromAsset(view.getContext().getAssets(), "emojione-android.ttf");

        mPhotoEditor = new PhotoEditor.Builder(view.getContext(), mPhotoEditorView)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                .setDefaultTextTypeface(mEmojiTypeFace)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        color_Recycler.setLayoutManager(layoutManager);
        color_Recycler.setHasFixedSize(true);
        colorPickerAdapter = new ColorPickerAdapter(view.getContext());
        colorPickerAdapter.setOnColorPickerClickListener(new ColorPickerAdapter.OnColorPickerClickListener() {
            @Override
            public void onColorPickerClickListener(int colorCode) {
                mPhotoEditorView.setBackgroundColor(colorCode);
                color_Recycler.setVisibility(View.GONE);
                linearLayoutCompat.setVisibility(View.VISIBLE);

            }
        });
        viwww = view;
        return view;
    }

    @Override
    public void onEditTextChangeListener(final View rootView, String text, int colorCode) {
        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show((AppCompatActivity) viwww.getContext(), text, colorCode);
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode) {
                final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                styleBuilder.withTextColor(colorCode);
                styleBuilder.withTextFont(mEmojiTypeFace );
                Log.d("TAG", "From TAGFRAGMENT - "+inputText);
                mPhotoEditor.editText(rootView, inputText, styleBuilder);
            }
        });
    }

    private void ButtonWorks() {

        /*mPhotoEditorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openText(view);
            }
        });*/

        background_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutCompat.setVisibility(View.GONE);
                color_Recycler.setVisibility(View.VISIBLE);
                color_Recycler.setAdapter(colorPickerAdapter);
            }
        });

        sticker_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStickerBSFragment.show(getFragmentManager() , mStickerBSFragment.getTag());
            }
        });

        text_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openText(view);
            }
        });
    }

    private void openText(View view) {
        TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show((AppCompatActivity) view.getContext());
        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String inputText, int colorCode) {
                final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                styleBuilder.withTextColor(colorCode);
                styleBuilder.withTextFont(mEmojiTypeFace);
                Log.d("TAG", "From TAGFRAGMENT openText method - "+inputText);
                mPhotoEditor.addText(inputText, styleBuilder);
            }
        });
    }

    private void initViews(View view) {
        mPhotoEditorView = view.findViewById(R.id.photoEditorView2);
        background_img = view.findViewById(R.id.background_images);
        text_img = view.findViewById(R.id.text);
        sticker_img = view.findViewById(R.id.sticker);
        color_Recycler = view.findViewById(R.id.back_recycler);
        linearLayoutCompat = view.findViewById(R.id.linearLayout);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onColorChanged(int colorCode) {

    }

    @Override
    public void onOpacityChanged(int opacity) {

    }

    @Override
    public void onBrushSizeChanged(int brushSize) {

    }

    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
    }

}
