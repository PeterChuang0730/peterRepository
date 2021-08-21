package com.example.taipeizoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taipeizoo.R;
import com.example.taipeizoo.model.Plant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.taipeizoo.Utility.Util.isHttpOrHttpsUrl;

public class PlantRecyclerAdapter extends RecyclerView.Adapter<PlantRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private ArrayList<Plant> plantList;
    private final AdapterView.OnItemClickListener onItemClickListener;

    public PlantRecyclerAdapter(Context context, AdapterView.OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.plant_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.plantName = view.findViewById(R.id.plantName);
        holder.plantImage = view.findViewById(R.id.plantImage);
        holder.plantAlsoKnown = view.findViewById(R.id.plantAlsoKnown);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plant plant = plantList.get(position);

        holder.plantName.setText(plant.getName_Ch());

        Glide.with(mContext).clear(holder.plantImage);

        if (plant.getPictureURL() != null) {
            if (isHttpOrHttpsUrl(plant.getPictureURL())) {
                Glide.with(mContext)
                        .load(plant.getPictureURL())
                        .into(holder.plantImage);
            }
        } else if (plant.getAlsoKnown() != null) {
            if (isHttpOrHttpsUrl(plant.getAlsoKnown())) {
                Glide.with(mContext)
                        .load(plant.getAlsoKnown())
                        .into(holder.plantImage);
            }
        } else if (plant.getPictureURL2() != null) {
            if (isHttpOrHttpsUrl(plant.getPictureURL2())) {
                Glide.with(mContext)
                        .load(plant.getPictureURL2())
                        .into(holder.plantImage);
            }
        } else if (plant.getPictureURL3() != null) {
            if (isHttpOrHttpsUrl(plant.getPictureURL3())) {
                Glide.with(mContext)
                        .load(plant.getPictureURL3())
                        .into(holder.plantImage);
            }
        }

        holder.plantAlsoKnown.setText(plant.getAlsoKnown());
    }

    public void refreshData(ArrayList<Plant> data) {
        this.plantList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (plantList == null) {
            return 0;
        } else {
            return plantList.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView plantName;
        ImageView plantImage;
        TextView plantAlsoKnown;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }
}


