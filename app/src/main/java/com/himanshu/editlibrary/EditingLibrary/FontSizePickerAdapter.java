package com.himanshu.editlibrary.EditingLibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        colorPickerColors.add(62f);
        colorPickerColors.add(64f);
        colorPickerColors.add(66f);
        colorPickerColors.add(68f);
        colorPickerColors.add(70f);
        colorPickerColors.add(72f);
        colorPickerColors.add(74f);
        colorPickerColors.add(76f);
        colorPickerColors.add(78f);
        colorPickerColors.add(80f);
        colorPickerColors.add(82f);
        colorPickerColors.add(84f);
        colorPickerColors.add(86f);
        colorPickerColors.add(88f);
        colorPickerColors.add(90f);
        return colorPickerColors;
    }
}


