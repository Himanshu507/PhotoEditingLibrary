package com.himanshu.editlibrary.EditLib;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;

import com.himanshu.editlibrary.TextEdit.AutoFitTextView;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is used to wrap the styles to apply on the TextView on {@link PhotoEditor#addText(String, TextStyleBuilder)} and {@link PhotoEditor#editText(View, String, TextStyleBuilder)}
 * </p>
 *
 * @author <a href="https://github.com/Sulfkain">Christian Caballero</a>
 * @since 14/05/2019
 */
public class TextStyleBuilder {

    private Map<TextStyle, Object> values = new HashMap<>();
    protected Map<TextStyle, Object> getValues() { return values; }

    public void withTextAlpha(@NonNull int value){
        values.put(TextStyle.TEXT_ALPHA, value);
    }

    /**
     * Set this textSize style
     *
     * @param size Size to apply on text
     */
    public void withTextSize(@NonNull float size) {
        values.put(TextStyle.SIZE, size);
    }

    /**
     * Set this color style
     *
     * @param color Color to apply on text
     */
    public void withTextColor(@NonNull int color) {
        values.put(TextStyle.COLOR, color);
    }

    /**
     * Set this {@link Typeface} style
     *
     * @param textTypeface TypeFace to apply on text
     */
    public void withTextFont(@NonNull Typeface textTypeface) {
        values.put(TextStyle.FONT_FAMILY, textTypeface);
    }

    /**
     * Set this gravity style
     *
     * @param gravity Gravity style to apply on text
     */
    public void withGravity(@NonNull int gravity) {
        values.put(TextStyle.GRAVITY, gravity);
    }

    /**
     * Set this background color
     *
     * @param background Background color to apply on text, this method overrides the preview set on {@link TextStyleBuilder#withBackgroundDrawable(Drawable)}
     */
    public void withBackgroundColor(@NonNull int background) {
        values.put(TextStyle.BACKGROUND, background);
    }

    /**
     * Set this background {@link Drawable}, this method overrides the preview set on {@link TextStyleBuilder#withBackgroundColor(int)}
     *
     * @param bgDrawable Background drawable to apply on text
     */
    public void withBackgroundDrawable(@NonNull Drawable bgDrawable) {
        values.put(TextStyle.BACKGROUND, bgDrawable);
    }

    /**
     * Set this textAppearance style
     *
     * @param textAppearance Text style to apply on text
     */
    public void withTextAppearance(@NonNull int textAppearance) {
        values.put(TextStyle.TEXT_APPEARANCE, textAppearance);
    }

    /**
     * Method to apply all the style setup on this Builder}
     *
     * @param textView TextView to apply the style
     */
    void applyStyle(@NonNull AutoFitTextView textView) {
        for (Map.Entry<TextStyle, Object> entry : values.entrySet()) {
            switch (entry.getKey()) {
                case SIZE: {
                    final float size = (float) entry.getValue();
                    applyTextSize(textView, size);
                }
                break;

                case COLOR: {
                    final int color = (int) entry.getValue();
                    applyTextColor(textView, color);
                }
                break;

                case FONT_FAMILY: {
                    final Typeface typeface = (Typeface) entry.getValue();
                    applyFontFamily(textView, typeface);
                }
                break;

                case GRAVITY: {
                    final int gravity = (int) entry.getValue();
                    applyGravity(textView, gravity);
                }
                break;

                case BACKGROUND: {
                    if (entry.getValue() instanceof Drawable) {
                        final Drawable bg = (Drawable) entry.getValue();
                        applyBackgroundDrawable(textView, bg);

                    } else if (entry.getValue() instanceof Integer) {
                        final int color = (Integer) entry.getValue();
                        applyBackgroundColor(textView, color);
                    }
                }
                break;

                case TEXT_APPEARANCE: {
                    if (entry.getValue() instanceof Integer) {
                        final int styleAppearance = (Integer)entry.getValue();
                        applyTextAppearance(textView, styleAppearance);
                    }
                }
                break;

                case TEXT_ALPHA:{
                    if (entry.getValue() instanceof Float){
                        final int alpha = (Integer) entry.getValue();
                        applyTextAlpha(textView,alpha);
                    }
                }
            }
        }
    }

    private void applyTextAlpha(AutoFitTextView textView, int value){
        textView.getBackground().setAlpha(value);
    }

    protected void applyTextSize(AutoFitTextView textView, float size) {
        textView.setTextSize(size);
    }

    protected void applyTextColor(AutoFitTextView textView, int color) {
        textView.setTextColor(color);
    }

    protected void applyFontFamily(AutoFitTextView textView, Typeface typeface) {
        textView.setTypeface(typeface);
    }

    protected void applyGravity(AutoFitTextView textView, int gravity) {
        textView.setGravity(gravity);
    }

    protected void applyBackgroundColor(AutoFitTextView textView, int color) {
        textView.setBackgroundColor(color);
    }

    protected void applyBackgroundDrawable(AutoFitTextView textView, Drawable bg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(bg);
        } else {
            textView.setBackgroundDrawable(bg);
        }
    }

    protected void applyTextAppearance(AutoFitTextView textView, int styleAppearance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(styleAppearance);
        } else {
            textView.setTextAppearance(textView.getContext(), styleAppearance);
        }
    }

    /**
     * Enum to maintain current supported style properties used on on {@link PhotoEditor#addText(String, TextStyleBuilder)} and {@link PhotoEditor#editText(View, String, TextStyleBuilder)}
     */
    protected enum TextStyle {
        SIZE("TextSize"),
        COLOR("TextColor"),
        GRAVITY("Gravity"),
        FONT_FAMILY("FontFamily"),
        BACKGROUND("Background"),
        TEXT_APPEARANCE("TextAppearance"),
        TEXT_ALPHA("TextAlpha");

        TextStyle(String property) {
            this.property = property;
        }

        private String property;
        public String getProperty() {return property;}
    }
}
