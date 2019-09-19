package com.himanshu.editlibrary.EditingLibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.himanshu.editlibrary.R;

import java.util.ArrayList;
import java.util.List;

public class FontSizePickerAdapter extends RecyclerView.Adapter<FontSizePickerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Float> colorPickerColors;
    private FontSizePickerAdapter.FontSizePickerClickListener onColorPickerClickListener;

    FontSizePickerAdapter(@NonNull Context context, @NonNull List<Float> colorPickerColors) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.colorPickerColors = colorPickerColors;
    }

    public FontSizePickerAdapter(@NonNull Context context) {
        this(context, getDefaultColors(context));
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public FontSizePickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.font_picker_item_list, parent, false);
        return new FontSizePickerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FontSizePickerAdapter.ViewHolder holder, int position) {
        String value = String.valueOf(colorPickerColors.get(position));
        Drawable mDrawable = ContextCompat.getDrawable(context, R.drawable.colors_item_background);
        holder.colorPickerView.setBackground(mDrawable);
        holder.colorPickerView.setText(value);
    }

    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }

    public void setOnColorPickerClickListener(FontSizePickerAdapter.FontSizePickerClickListener onColorPickerClickListener) {
        this.onColorPickerClickListener = onColorPickerClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView colorPickerView;

        public ViewHolder(View itemView) {
            super(itemView);
            colorPickerView = itemView.findViewById(R.id.color_picker_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onColorPickerClickListener != null)
                        onColorPickerClickListener.onColorPickerClickListener(colorPickerColors.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface FontSizePickerClickListener {
        void onColorPickerClickListener(Float colorCode);
    }

    public static List<Float> getDefaultColors(Context context) {
        ArrayList<Float> colorPickerColors = new ArrayList<>();
        colorPickerColors.add(04f);
        colorPickerColors.add(06f);
        colorPickerColors.add(08f);
        colorPickerColors.add(10f);
        colorPickerColors.add(12f);
        colorPickerColors.add(14f);
        colorPickerColors.add(16f);
        colorPickerColors.add(18f);
        colorPickerColors.add(20f);
        colorPickerColors.add(22f);
        colorPickerColors.add(24f);
        colorPickerColors.add(26f);
        colorPickerColors.add(28f);
        colorPickerColors.add(30f);
        colorPickerColors.add(32f);
        colorPickerColors.add(34f);
        colorPickerColors.add(36f);
        colorPickerColors.add(38f);
        colorPickerColors.add(40f);
        colorPickerColors.add(42f);
        colorPickerColors.add(44f);
        colorPickerColors.add(46f);
        colorPickerColors.add(48f);
        colorPickerColors.add(50f);
        colorPickerColors.add(52f);
        colorPickerColors.add(54f);
        colorPickerColors.add(56f);
        colorPickerColors.add(58f);
        colorPickerColors.add(60f);
        return colorPickerColors;
    }
}


