package com.himanshu.editlibrary.EditingLibrary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.himanshu.editlibrary.R;

public class Space_bottom extends BottomSheetDialogFragment {

    TextView min, nor , max;
    Button bottom_space;
    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.space_bottom, container, false);
        min = v.findViewById(R.id.min);
        nor = v.findViewById(R.id.normal);
        max = v.findViewById(R.id.max);
        bottom_space = v.findViewById(R.id.bottom);

        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.set_height(90);
                dismiss();
            }
        });

        nor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.set_height(120);
                dismiss();
            }
        });

        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.set_height(150);
                dismiss();
            }
        });

        bottom_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.show_bottomSpace();
                dismiss();
            }
        });

        return v;
    }

    public interface BottomSheetListener {
        void set_height(int height);
        void show_bottomSpace();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

}
