package mz.ac.luis.seia.finacieme.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class IconSpinnerAdapter extends ArrayAdapter<Drawable> {
    private LayoutInflater inflater;

    public IconSpinnerAdapter(Context context, List<Drawable> icons) {
        super(context, 0, icons);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            imageView = (ImageView) convertView;
        }
        Drawable icon = getItem(position);
        imageView.setImageDrawable(icon);
        return imageView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

