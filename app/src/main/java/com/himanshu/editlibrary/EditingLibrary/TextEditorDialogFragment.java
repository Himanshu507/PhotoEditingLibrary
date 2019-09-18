package com.himanshu.editlibrary.EditingLibrary;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.himanshu.editlibrary.R;
import com.himanshu.editlibrary.TextEdit.AutoFitEditText;
import com.himanshu.editlibrary.Utils.AutoFitEditTextUtil;


public class TextEditorDialogFragment extends DialogFragment{

    public static final String TAG = TextEditorDialogFragment.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    private AutoFitEditText mAddTextEditText;
    private ImageView mAddTextDoneTextView;
    private InputMethodManager mInputMethodManager;
    private int mColorCode;
    private TextEditor mTextEditor;
    RelativeLayout rootviewtext, font_size_relative_layout,font_picker_relative_layout,add_text_color_picker_relative_layout ;
    //Spinner size_spinner;
    Context context;
    SeekBar color_opacity;
    ImageView opacity_img, color_img, font_img, font_size_img, textsize_img;
    boolean show_color_opacity = false;
    String[] font_Size = { "Small", "Medium", "Large"};
    int alpha_text = 1;
    public interface TextEditor {
        void onDone(String inputText, int colorCode, float textSize, Typeface font, int alpha_Text);
    }

    public void initAutoFitEditText() {
        mAddTextEditText.setEnabled(true);
        mAddTextEditText.setFocusableInTouchMode(true);
        mAddTextEditText.setFocusable(true);
        mAddTextEditText.setEnableSizeCache(false);
        //might cause crash on some devices
        mAddTextEditText.setMovementMethod(null);
        // can be added after layout inflation;
        mAddTextEditText.setMaxHeight(250);
        //don't forget to add min text size programmatically
        mAddTextEditText.setMinTextSize(10f);

        AutoFitEditTextUtil.setNormalization((AppCompatActivity) getActivity(), rootviewtext, mAddTextEditText);
    }

    //Show dialog with provide text and text color
    public static TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity,
                                                @NonNull String inputText,
                                                @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        TextEditorDialogFragment fragment = new TextEditorDialogFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    //Show dialog with default text input as empty and text color white
    public static TextEditorDialogFragment show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity,
                "", ContextCompat.getColor(appCompatActivity, R.color.white));
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_text_dialog, container, false);
        context = view.getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        opacity_img = view.findViewById(R.id.opacity_img);
        opacity_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!show_color_opacity){
                    color_opacity.setVisibility(View.VISIBLE);
                    show_color_opacity = true;
                }else {
                    color_opacity.setVisibility(View.GONE);
                    show_color_opacity = false;
                }
            }
        });

        mAddTextEditText = view.findViewById(R.id.add_text_edit_text);
        color_opacity = view.findViewById(R.id.color_opacity);
        color_opacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //alpha_text = (float) i / 255;
                //mAddTextEditText.setAlpha(alpha_text);
                alpha_text = i;
                mAddTextEditText.getBackground().setAlpha(i);
                GradientDrawable drawable = (GradientDrawable)mAddTextEditText.getBackground();
                drawable.setAlpha(alpha_text);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*size_spinner = view.findViewById(R.id.spinner_text);
        ArrayAdapter aa = new ArrayAdapter(view.getContext(),android.R.layout.simple_spinner_item,font_Size);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        size_spinner.setAdapter(aa);

        size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    mAddTextEditText.setTextSize(40f);
                if (i == 1)
                    mAddTextEditText.setTextSize(60f);
                if (i == 2)
                    mAddTextEditText.setTextSize(90f);
                Toast.makeText(context,font_Size[i],Toast.LENGTH_SHORT).show();
            }
*/
           /* @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast.makeText(context,font_Size[i],Toast.LENGTH_SHORT).show();
                mAddTextEditText.setTextSize(60f);
            }
        });*/
        //Relative Layout

        add_text_color_picker_relative_layout = view.findViewById(R.id.add_text_color_picker_relative_layout);
        font_picker_relative_layout = view.findViewById(R.id.font_picker_relative_layout);
        font_size_relative_layout = view.findViewById(R.id.font_size_relative_layout);

        //Text Menu images
        color_img = view.findViewById(R.id.color_img);
        font_img = view.findViewById(R.id.font_img);
        font_size_img = view.findViewById(R.id.font_size_img);

        color_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_text_color_picker_relative_layout.setVisibility(View.VISIBLE);
                font_picker_relative_layout.setVisibility(View.GONE);
                font_size_relative_layout.setVisibility(View.GONE);
                textsize_img.setVisibility(View.VISIBLE);
            }
        });

        font_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_text_color_picker_relative_layout.setVisibility(View.GONE);
                font_picker_relative_layout.setVisibility(View.VISIBLE);
                font_size_relative_layout.setVisibility(View.GONE);
                textsize_img.setVisibility(View.VISIBLE);
            }
        });

        font_size_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_text_color_picker_relative_layout.setVisibility(View.GONE);
                font_picker_relative_layout.setVisibility(View.GONE);
                font_size_relative_layout.setVisibility(View.VISIBLE);
                textsize_img.setVisibility(View.VISIBLE);
            }
        });

        textsize_img = view.findViewById(R.id.textsize_img);

        rootviewtext = view.findViewById(R.id.rootviewtext);
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mAddTextDoneTextView = view.findViewById(R.id.add_text_done_tv);
        initAutoFitEditText();
        //Setup the color picker for text color
        RecyclerView addTextColorPickerRecyclerView = view.findViewById(R.id.add_text_color_picker_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        addTextColorPickerRecyclerView.setLayoutManager(layoutManager);
        addTextColorPickerRecyclerView.setHasFixedSize(true);
        ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(getActivity());
        //This listener will change the text color when clicked on any color from picker
        colorPickerAdapter.setOnColorPickerClickListener(new ColorPickerAdapter.OnColorPickerClickListener() {
            @Override
            public void onColorPickerClickListener(int colorCode) {
                mColorCode = colorCode;
                mAddTextEditText.setTextColor(colorCode);
                color_opacity.getProgressDrawable().setColorFilter(mColorCode, PorterDuff.Mode.MULTIPLY);
            }
        });

        RecyclerView addFontRecycler = view.findViewById(R.id.font_picker_recycler_view);
        FontPickerAdapter fontPickerAdapter = new FontPickerAdapter(getActivity());
        LinearLayoutManager layoutManagerr = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        fontPickerAdapter.setOnColorPickerClickListener(new FontPickerAdapter.FontPickerClickListener() {
            @Override
            public void onColorPickerClickListener(Typeface colorCode) {
                mAddTextEditText.setTypeface(colorCode);
            }
        });

        addTextColorPickerRecyclerView.setAdapter(colorPickerAdapter);
        addFontRecycler.setLayoutManager(layoutManagerr);
        addFontRecycler.setHasFixedSize(true);
        addFontRecycler.setAdapter(fontPickerAdapter);

        final RecyclerView font_size_recycler_view = view.findViewById(R.id.font_size_recycler_view);
        LinearLayoutManager layoutManage = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        FontSizePickerAdapter fontSizePickerAdapter = new FontSizePickerAdapter(getActivity());
        fontSizePickerAdapter.setOnColorPickerClickListener(new FontSizePickerAdapter.FontSizePickerClickListener() {
            @Override
            public void onColorPickerClickListener(Float colorCode) {
                mAddTextEditText.setTextSize(colorCode);
            }
        });
         font_size_recycler_view.setLayoutManager(layoutManage);
         font_size_recycler_view.setHasFixedSize(true);
         font_size_recycler_view.setAdapter(fontSizePickerAdapter);

        textsize_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_text_color_picker_relative_layout.setVisibility(View.GONE);
                font_picker_relative_layout.setVisibility(View.GONE);
                font_size_relative_layout.setVisibility(View.GONE);
                textsize_img.setVisibility(View.GONE);
            }
        });

        mAddTextEditText.setText(getArguments().getString(EXTRA_INPUT_TEXT));
        mColorCode = getArguments().getInt(EXTRA_COLOR_CODE);
        mAddTextEditText.setTextColor(mColorCode);
        mAddTextEditText.setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(),"permanentmarker.ttf"));
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Make a callback on activity when user is done with text editing
        mAddTextDoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dismiss();
                String inputText = mAddTextEditText.getText().toString();
                if (!TextUtils.isEmpty(inputText) && mTextEditor != null) {
                    mTextEditor.onDone(inputText, mColorCode, mAddTextEditText.getTextSize(), mAddTextEditText.getTypeface(), alpha_text);
                    Log.d("TAG", inputText);
                }
            }
        });

    }

    //Callback to listener if user is done with text editing
    public void setOnTextEditorListener(TextEditor textEditor) {
        mTextEditor = textEditor;
    }
}
