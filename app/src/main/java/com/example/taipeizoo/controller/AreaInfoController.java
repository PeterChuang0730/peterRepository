package com.example.taipeizoo.controller;

import com.example.taipeizoo.fragment.MainFragment;
import com.example.taipeizoo.model.Area;

import java.util.ArrayList;

public class AreaInfoController {
    private ArrayList<Area> model;
    private MainFragment view;

    public AreaInfoController(ArrayList<Area> model, MainFragment view) {
        this.model = model;
        this.view = view;
    }

    public ArrayList<Area> getData() {
        return model;
    }

    public void updateView() {
        view.refreshRecyclerView();
    }

}
