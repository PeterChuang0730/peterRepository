package com.example.taipeizoo.controller;

import com.example.taipeizoo.fragment.MainFragment;
import com.example.taipeizoo.model.Area;

import java.util.ArrayList;

public class AreaInfoController {
    private ArrayList<Area> model;
    private MainFragment view;

    public AreaInfoController(MainFragment view) {
        this.view = view;
    }

    public void setData(ArrayList<Area> model) {
        this.model = model;
    }

    public ArrayList<Area> getData() {
        return model;
    }

    public void updateView() {
        view.refreshRecyclerView();
    }

}
