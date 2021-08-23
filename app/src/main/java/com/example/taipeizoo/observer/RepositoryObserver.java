package com.example.taipeizoo.observer;

import com.example.taipeizoo.model.Area;

import java.util.ArrayList;

public interface RepositoryObserver {
    void onAreaListDataChanged(ArrayList<Area> areaList);
}
