package com.example.taipeizoo.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.taipeizoo.R;
import com.example.taipeizoo.model.Area;
import com.example.taipeizoo.model.Plant;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.taipeizoo.controller.MainFragment.plantList;
import static com.example.taipeizoo.webservice.OkManager.AREADATA;
import static com.example.taipeizoo.webservice.OkManager.PLANTLIST;

public class AreaFragment extends Fragment {

    private Area selectedArea;

    private Context mContext;

    private ArrayList<Plant> selectedPlantList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_area, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            ImageView areaImage = Objects.requireNonNull(getView()).findViewById(R.id.areaImage);

            TextView areaName = Objects.requireNonNull(getView()).findViewById(R.id.areaName);
            TextView areaMemo = Objects.requireNonNull(getView()).findViewById(R.id.areaMemo);
            TextView areaCategory = Objects.requireNonNull(getView()).findViewById(R.id.areaCategory);
            TextView areaInfo = Objects.requireNonNull(getView()).findViewById(R.id.areaInfo);
            TextView areaPlant = Objects.requireNonNull(getView()).findViewById(R.id.areaPlant);

            Bundle bundle = getArguments();
            if (bundle != null) {
                selectedArea = (Area) bundle.getSerializable(AREADATA);

                if (selectedArea != null) {
                    if (getActivity() != null) {
                        getActivity().setTitle(selectedArea.getName());
                    }

                    areaName.setText(selectedArea.getInfo());
                    areaMemo.setText(selectedArea.getMemo());
                    areaCategory.setText(selectedArea.getCategory());

                    Glide.with(mContext)
                            .load(selectedArea.getPictureURL())
                            .into(areaImage);

                    areaInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.equals(selectedArea.getURL(), "")) {
                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(selectedArea.getURL()));
                                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                startActivity(intent);
                            }
                        }
                    });

                    areaPlant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if (selectedPlantList != null) {
                                    if (selectedPlantList.size() > 0) {
                                        Fragment allPlantFragment = new AreaPlantsFragment();
                                        Bundle plantBundle = new Bundle();
                                        plantBundle.putSerializable(PLANTLIST, selectedPlantList);
                                        allPlantFragment.setArguments(plantBundle);

                                        if (getFragmentManager() != null) {
                                            getFragmentManager().beginTransaction()
                                                    .replace(R.id.mainLayout, allPlantFragment)
                                                    .addToBackStack(null)
                                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                                    .commit();
                                        }
                                    } else {
                                        Toast.makeText(mContext, getString(R.string.no_data),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception ignored) {

                            }
                        }
                    });
                }
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (plantList != null) {
            selectedPlantList.clear();
            for (int i = 0; i < plantList.size(); i++) {
                if (plantList.get(i) != null) {
                    if ((plantList.get(i).getLocation() != null) && (selectedArea != null)) {
                        if (!TextUtils.equals(plantList.get(i).getLocation(), "")) {
                            if (plantList.get(i).getLocation().contains(selectedArea.getName())) {
                                selectedPlantList.add(plantList.get(i));
                            }
                        }
                    }
                }
            }
        }

    }
}