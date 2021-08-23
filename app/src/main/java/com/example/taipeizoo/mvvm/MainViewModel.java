package com.example.taipeizoo.mvvm;

import com.example.taipeizoo.model.Area;

import java.util.ArrayList;

public class MainViewModel {

    private DataModel dataModel = new DataModel();

    public void refresh() {
        dataModel.retrieveData(new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(ArrayList<Area> areaList) {
                // TODO: exposes data to View
            }
        });
    }
}
