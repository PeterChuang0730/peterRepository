package com.example.taipeizoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taipeizoo.R;
import com.example.taipeizoo.model.Area;

import java.util.ArrayList;

public class CustomAreaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Area> areaList;

    public CustomAreaAdapter(Context context, ArrayList<Area> areaList) {

        this.context = context;
        this.areaList = areaList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return areaList.size();
    }

    @Override
    public Object getItem(int position) {
        return areaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                row = inflater.inflate(R.layout.area_item, parent, false);
            }
        }

        TextView areaName = row.findViewById(R.id.areaName);
        areaName.setText(areaList.get(position).getName());

        ImageView areaImage = row.findViewById(R.id.areaImage);
        Glide.with(context)
                .load(areaList.get(position).getPictureURL())
                .into(areaImage);

        TextView areaInfo = row.findViewById(R.id.areaInfo);
        areaInfo.setText(areaList.get(position).getInfo());

        TextView areaMemo = row.findViewById(R.id.areaMemo);
        areaMemo.setText(areaList.get(position).getMemo());

        return row;
    }
}