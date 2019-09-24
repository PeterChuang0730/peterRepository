package com.example.taipeizoo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.taipeizoo.R;
import com.example.taipeizoo.model.Plant;

import java.util.Objects;

import static com.example.taipeizoo.Utility.Util.isHttpOrHttpsUrl;
import static com.example.taipeizoo.webservice.OkManager.PLANTDETAIL;

public class PlantDetailFragment extends Fragment {
    private Activity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plant_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            ImageView plantImage = Objects.requireNonNull(getView()).findViewById(R.id.plantImage);

            TextView plantChName = Objects.requireNonNull(getView()).findViewById(R.id.plantChName);
            TextView plantEnName = Objects.requireNonNull(getView()).findViewById(R.id.plantEnName);
            TextView plantAlias = Objects.requireNonNull(getView()).findViewById(R.id.plantAlias);
            TextView plantFeature = Objects.requireNonNull(getView()).findViewById(R.id.plantFeature);
            TextView plantLastUpdate = Objects.requireNonNull(getView()).findViewById(R.id.plantLastUpdate);

            Bundle bundle = getArguments();
            if (bundle != null) {
                Plant selectedPlant = (Plant) bundle.getSerializable(PLANTDETAIL);

                if (selectedPlant != null) {
                    if (mActivity != null) {
                        mActivity.setTitle(selectedPlant.getName_Ch());
                    }

                    plantChName.setText(selectedPlant.getName_Ch());
                    plantEnName.setText(selectedPlant.getName_En());
                    plantAlias.setText(selectedPlant.getAlsoKnown());
                    plantFeature.setText(selectedPlant.getFeature());
                    plantLastUpdate.setText(selectedPlant.getUpdate());

                    if (selectedPlant.getPictureURL() != null) {
                        if (isHttpOrHttpsUrl(selectedPlant.getPictureURL())) {
                            Glide.with(mActivity)
                                    .load(selectedPlant.getPictureURL())
                                    .into(plantImage);
                        }
                    } else if (selectedPlant.getAlsoKnown() != null) {
                        if (isHttpOrHttpsUrl(selectedPlant.getAlsoKnown())) {
                            Glide.with(mActivity)
                                    .load(selectedPlant.getAlsoKnown())
                                    .into(plantImage);
                        }
                    } else if (selectedPlant.getPictureURL2() != null) {
                        if (isHttpOrHttpsUrl(selectedPlant.getPictureURL2())) {
                            Glide.with(mActivity)
                                    .load(selectedPlant.getPictureURL2())
                                    .into(plantImage);
                        }
                    } else if (selectedPlant.getPictureURL3() != null) {
                        if (isHttpOrHttpsUrl(selectedPlant.getPictureURL3())) {
                            Glide.with(mActivity)
                                    .load(selectedPlant.getPictureURL3())
                                    .into(plantImage);
                        }
                    }
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
    }
}