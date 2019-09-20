package com.example.taipeizoo.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.taipeizoo.adapter.AreaRecyclerAdapter;
import com.example.taipeizoo.model.Area;
import com.example.taipeizoo.model.Plant;
import com.example.taipeizoo.view.WaitProgressDialog;
import com.example.taipeizoo.webservice.OkManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.taipeizoo.webservice.OkManager.AREADATA;
import static com.example.taipeizoo.webservice.OkManager.RESULT;
import static com.example.taipeizoo.webservice.OkManager.RESULTS;

public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<Area> areaList;
    static ArrayList<Plant> plantList;

    private AreaRecyclerAdapter adapter;
    private Activity mActivity;

    private OkManager manager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mActivity = (Activity) context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        manager = OkManager.getInstance();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mActivity != null) {
            mActivity.setTitle(R.string.app_name);
        }

        adapter = new AreaRecyclerAdapter(mActivity, this);

        RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);
        // 設定LayoutManager為LinearLayout
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);

        if (areaList != null) {
            adapter.refreshData(areaList);
        } else {
            WaitProgressDialog.showProgressDialog(mActivity, getString(R.string.loading_data));

            manager.asyncJsonStringByURL(OkManager.API_ALL_AREA, new OkManager.CallbackResponse() {
                @Override
                public void onResponse(String result) {
                    WaitProgressDialog.closeDialog();

                    if (!TextUtils.equals(result, "")) {
                        JsonObject jo = new JsonParser().parse(result).getAsJsonObject();
                        JsonObject jsonResult = jo.getAsJsonObject(RESULT);
                        JsonArray arrayResults = jsonResult.getAsJsonArray(RESULTS);

                        if (arrayResults != null) {
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<List<Area>>() {
                            }.getType();
                            areaList = gson.fromJson(arrayResults, collectionType);

                            if (areaList != null) {
                                adapter.refreshData(areaList);
                            }
                        }
                    }
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (plantList == null) {
            manager.asyncJsonStringByURL(OkManager.API_ALL_PLANT, new OkManager.CallbackResponse() {
                @Override
                public void onResponse(String result) {
                    if (!TextUtils.equals(result, "")) {
                        JsonObject jo = new JsonParser().parse(result).getAsJsonObject();
                        JsonObject jsonResult = jo.getAsJsonObject(RESULT);
                        JsonArray arrayResults = jsonResult.getAsJsonArray(RESULTS);

                        if (arrayResults != null) {
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<List<Plant>>() {
                            }.getType();
                            plantList = gson.fromJson(arrayResults, collectionType);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (getFragmentManager() != null) {
            try {
                Fragment plantFragment = new AreaFragment();
                Bundle plantBundle = new Bundle();
                plantBundle.putSerializable(AREADATA, areaList.get(i));
                plantFragment.setArguments(plantBundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.mainLayout, plantFragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            } catch (Exception ignored) {

            }
        }
    }
}