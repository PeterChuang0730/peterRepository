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
import com.example.taipeizoo.model.Area;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AreaRecyclerAdapter extends RecyclerView.Adapter<AreaRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Area> areaList;
    private AdapterView.OnItemClickListener onItemClickListener;

    public AreaRecyclerAdapter(Context context, ArrayList<Area> data,
                               AdapterView.OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.areaList = data;
        this.onItemClickListener = onItemClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.area_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.areaName = view.findViewById(R.id.areaName);
        holder.areaImage = view.findViewById(R.id.areaImage);
        holder.areaInfo = view.findViewById(R.id.areaInfo);
        holder.areaMemo = view.findViewById(R.id.areaMemo);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Area area = areaList.get(position);

        holder.areaName.setText(area.getName());

        Glide.with(mContext).clear(holder.areaImage);

        Glide.with(mContext)
                .load(area.getPictureURL())
                .into(holder.areaImage);

        holder.areaInfo.setText(area.getInfo());
        holder.areaMemo.setText(area.getMemo());
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView areaName;
        ImageView areaImage;
        TextView areaInfo;
        TextView areaMemo;

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


