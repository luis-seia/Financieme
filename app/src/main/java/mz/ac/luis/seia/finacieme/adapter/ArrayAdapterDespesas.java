package mz.ac.luis.seia.finacieme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import mz.ac.luis.seia.finacieme.R;

public class ArrayAdapterDespesas extends ArrayAdapter<String> {

    private int[] icons;

    public ArrayAdapterDespesas(Context context, int layoutResourceId, String[] data, int[] icons) {
        super(context, layoutResourceId, data);
        this.icons = icons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.item_spinner_despesas, parent, false);

            holder = new ViewHolder();
            holder.icon = (ImageView) row.findViewById(R.id.item_icon);
            holder.text = (TextView) row.findViewById(R.id.item_text);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.icon.setImageResource(icons[position]);
        holder.text.setText(getItem(position));

        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        super.getDropDownView(position, convertView, parent);
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.custom_dropdown_item, parent, false);

            holder = new ViewHolder();
            holder.icon = (ImageView) row.findViewById(R.id.item_icon);
            holder.text = (TextView) row.findViewById(R.id.item_text);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.icon.setImageResource(icons[position]);
        holder.text.setText(getItem(position));

        return row;
    }

    static class ViewHolder {
        ImageView icon;
        TextView text;
    }

}
