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
import com.example.taipeizoo.model.Plant;

import java.util.ArrayList;

public class CustomPlantAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Plant> plantList;

    public CustomPlantAdapter(Context context, ArrayList<Plant> plantList) {

        this.context = context;
        this.plantList = plantList;
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
        return plantList.size();
    }

    @Override
    public Object getItem(int position) {
        return plantList.get(position);
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
                row = inflater.inflate(R.layout.plant_item, parent, false);
            }
        }

        TextView plantName = row.findViewById(R.id.plantName);
        plantName.setText(plantList.get(position).getName_Ch());

        ImageView plantImage = row.findViewById(R.id.plantImage);

        if (plantList.get(position).getPictureURL() != null) {
            if (plantList.get(position).getPictureURL().contains("http")) {
                Glide.with(context)
                        .load(plantList.get(position).getPictureURL())
                        .into(plantImage);
            }
        } else if (plantList.get(position).getAlsoKnown() != null) {
            if (plantList.get(position).getAlsoKnown().contains("http")) {
                Glide.with(context)
                        .load(plantList.get(position).getAlsoKnown())
                        .into(plantImage);
            }
        } else if (plantList.get(position).getPictureURL2() != null) {
            if (plantList.get(position).getPictureURL2().contains("http")) {
                Glide.with(context)
                        .load(plantList.get(position).getPictureURL2())
                        .into(plantImage);
            }
        } else if (plantList.get(position).getPictureURL3() != null) {
            if (plantList.get(position).getPictureURL3().contains("http")) {
                Glide.with(context)
                        .load(plantList.get(position).getPictureURL3())
                        .into(plantImage);
            }
        }

        TextView plantAlsoKnown = row.findViewById(R.id.plantAlsoKnown);
        plantAlsoKnown.setText(plantList.get(position).getAlsoKnown());

        return row;
    }
}