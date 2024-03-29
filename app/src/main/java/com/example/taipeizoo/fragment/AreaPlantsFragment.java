package com.example.taipeizoo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taipeizoo.R;
import com.example.taipeizoo.Utility.Util;
import com.example.taipeizoo.adapter.PlantRecyclerAdapter;
import com.example.taipeizoo.model.Plant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.taipeizoo.webservice.OkManager.PLANTDETAIL;
import static com.example.taipeizoo.webservice.OkManager.PLANTLIST;

public class AreaPlantsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private Activity mActivity;

    private ArrayList<Plant> plantList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mActivity = (Activity) context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_area_plants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mActivity != null) {
            mActivity.setTitle(R.string.plant_data);
        }

        PlantRecyclerAdapter adapter = new PlantRecyclerAdapter(mActivity, this);
        adapter.setHasStableIds(true);

        RecyclerView recyclerView = requireView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
                plantList = Util.cast(bundle.getSerializable(PLANTLIST));

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
        if (getActivity() != null) {
            try {
                Fragment plantDetailFragment = new PlantDetailFragment();
                Bundle plantBundle = new Bundle();
                plantBundle.putSerializable(PLANTDETAIL, plantList.get(i));
                plantDetailFragment.setArguments(plantBundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainLayout, plantDetailFragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            } catch (Exception ignored) {

            }
        }
    }
}