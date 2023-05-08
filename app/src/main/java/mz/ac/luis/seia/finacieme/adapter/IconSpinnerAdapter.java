package mz.ac.luis.seia.finacieme.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import mz.ac.luis.seia.finacieme.R;
import mz.ac.luis.seia.finacieme.helper.CustomItem;

public class IconSpinnerAdapter extends ArrayAdapter {
    private LayoutInflater inflater;

    public IconSpinnerAdapter(@NonNull Context context, ArrayList<CustomItem> customItem){
        super(context,0, customItem);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_item, parent, false);
        }
        CustomItem item = (CustomItem) getItem(position);
        ImageView dropDownIv = convertView.findViewById(R.id.ivDropDownLayout);
        TextView dropDownTv = convertView.findViewById(R.id.tvDropDownLayout);

        if(item !=null){
            dropDownIv.setImageResource(item.getSpinnerIconImage());
            dropDownTv.setText(item.getSpinnerName());
        }
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_item, parent, false);
       }
       CustomItem item = (CustomItem) getItem(position);
       ImageView ivIcon = convertView.findViewById(R.id.item_icon);
        TextView tvIcon = convertView.findViewById(R.id.item_text);

        if(item !=null){
            ivIcon.setImageResource(item.getSpinnerIconImage());
            tvIcon.setText(item.getSpinnerName());
        }
        return convertView;
    }
}

