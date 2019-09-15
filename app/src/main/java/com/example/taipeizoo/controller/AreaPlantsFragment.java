package com.example.taipeizoo.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.taipeizoo.R;
import com.example.taipeizoo.adapter.CustomPlantAdapter;
import com.example.taipeizoo.model.Plant;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.taipeizoo.webservice.OkManager.PLANTDETAIL;
import static com.example.taipeizoo.webservice.OkManager.PLANTLIST;

public class AreaPlantsFragment extends Fragment {

    private ListView listView;
    private CustomPlantAdapter customPlantAdapter;
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

        listView = Objects.requireNonNull(getView()).findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                if (getFragmentManager() != null) {
                    try {
                        Fragment plantDetailFragment = new PlantDetailFragment();
                        Bundle plantBundle = new Bundle();
                        plantBundle.putSerializable(PLANTDETAIL, plantList.get(arg2));
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
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
                plantList = (ArrayList<Plant>) bundle.getSerializable(PLANTLIST);

                if (plantList != null) {
                    if (plantList.size() > 0) {
                        customPlantAdapter = new CustomPlantAdapter(mContext, plantList);
                        listView.setAdapter(customPlantAdapter);
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
}