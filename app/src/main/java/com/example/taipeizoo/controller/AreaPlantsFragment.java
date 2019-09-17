package com.example.taipeizoo.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taipeizoo.R;
import com.example.taipeizoo.adapter.PlantRecyclerAdapter;
import com.example.taipeizoo.model.Plant;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.taipeizoo.webservice.OkManager.PLANTDETAIL;
import static com.example.taipeizoo.webservice.OkManager.PLANTLIST;

public class AreaPlantsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private RecyclerView recyclerView;
    private PlantRecyclerAdapter adapter;
    private Context mContext;

    private ArrayList<Plant> plantList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.plant_data);
        }

        adapter = new PlantRecyclerAdapter(mContext, this);

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
                plantList = (ArrayList<Plant>) bundle.getSerializable(PLANTLIST);

                if (plantList != null) {
                    if (plantList.size() > 0) {
                        adapter.refreshData(plantList);
                    }
                }
            } catch (Exception ignored) {

            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (getFragmentManager() != null) {
            try {
                Fragment plantDetailFragment = new PlantDetailFragment();
                Bundle plantBundle = new Bundle();
                plantBundle.putSerializable(PLANTDETAIL, plantList.get(i));
                plantDetailFragment.setArguments(plantBundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.mainLayout, plantDetailFragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            } catch (Exception ignored) {

            }
        }
    }
}