package com.example.taipeizoo.mvvm;

import androidx.databinding.ObservableField;

import com.example.taipeizoo.model.Area;

import java.util.ArrayList;

public class MainViewModel {
    public final ObservableField<ArrayList<Area>> mData = new ObservableField<>();
    private DataModel dataModel = new DataModel();

    public void refresh() {
        dataModel.retrieveData(new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(ArrayList<Area> areaList) {
                mData.set(areaList);
            }
        });
    }
}
