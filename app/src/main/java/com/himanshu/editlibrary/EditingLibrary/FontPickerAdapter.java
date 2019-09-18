package com.himanshu.editlibrary.EditingLibrary;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.himanshu.editlibrary.R;

import java.util.ArrayList;
import java.util.List;

public class FontPickerAdapter extends RecyclerView.Adapter<FontPickerAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Typeface> colorPickerColors;
    private FontPickerAdapter.FontPickerClickListener onColorPickerClickListener;

    FontPickerAdapter(@NonNull Context context, @NonNull List<Typeface> colorPickerColors) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.colorPickerColors = colorPickerColors;
    }

    public FontPickerAdapter(@NonNull Context context) {
        this(context, getDefaultColors(context));
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public FontPickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.font_picker_item_list, parent, false);
        return new FontPickerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FontPickerAdapter.ViewHolder holder, int position) {

        holder.colorPickerView.setTypeface(colorPickerColors.get(position));
        holder.colorPickerView.setText("Aa");

    }

    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }

    public void setOnColorPickerClickListener(FontPickerAdapter.FontPickerClickListener onColorPickerClickListener) {
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

    public interface FontPickerClickListener {
        void onColorPickerClickListener(Typeface colorCode);
    }

    public static List<Typeface> getDefaultColors(Context context) {
        ArrayList<Typeface> colorPickerColors = new ArrayList<>();
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"beyond_wonderland.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"Carrington.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"Caviar_Dreams_Bold.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"CaviarDreams.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"CaviarDreams_BoldItalic.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"CaviarDreams_Italic.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"emojione-android.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"Kingthings_Calligraphica_2.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"Kingthings_Calligraphica_Italic.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"Kingthings_Calligraphica_Light.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"Kingthings_Foundation.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"montezregular.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"pacifico.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"permanentmarker.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"RechtmanPlain.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"Redressed.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"vacation.ttf"));
        colorPickerColors.add(Typeface.createFromAsset(context.getAssets(),"voice.ttf"));
        return colorPickerColors;
    }
}

