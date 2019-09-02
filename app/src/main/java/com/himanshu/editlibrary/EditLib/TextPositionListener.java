package com.himanshu.editlibrary.EditLib;


import android.view.View;

public interface TextPositionListener {
    void onTextPositionChanged(float x, float y, View view);
    void onViewAngleChanged(float rotation,View view);
    void onScaleChanged(float scale,View view);
}
