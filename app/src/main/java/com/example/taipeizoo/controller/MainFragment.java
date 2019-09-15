package com.example.taipeizoo.controller;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.taipeizoo.R;
import com.example.taipeizoo.adapter.CustomAreaAdapter;
import com.example.taipeizoo.model.Area;
import com.example.taipeizoo.model.Plant;
import com.example.taipeizoo.view.WaitProgressDialog;
import com.example.taipeizoo.webservice.OkManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;

import static com.example.taipeizoo.view.WaitProgressDialog.waitProgressDialog;
import static com.example.taipeizoo.webservice.OkManager.AREADATA;
import static com.example.taipeizoo.webservice.OkManager.RESULT;
import static com.example.taipeizoo.webservice.OkManager.RESULTS;

public class MainFragment extends Fragment {
    private String API_ALL_AREA = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a";
    private String API_ALL_PLANT = "\thttps://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14";
    private ArrayList<Area> areaList;
    static ArrayList<Plant> plantList;

    private ListView listView;
    private CustomAreaAdapter customAreaAdapter;
    private Context mContext;

    private OkManager manager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        manager = OkManager.getInstance();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.app_name);
        }

        listView = Objects.requireNonNull(getView()).findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                if (getFragmentManager() != null) {
                    try {
                        Fragment plantFragment = new AreaFragment();
                        Bundle plantBundle = new Bundle();
                        plantBundle.putSerializable(AREADATA, areaList.get(arg2));
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
        });

        if (areaList != null) {
            customAreaAdapter = new CustomAreaAdapter(mContext, areaList);
            listView.setAdapter(customAreaAdapter);
        } else {
            waitProgressDialog(mContext, getString(R.string.loading_data));

            manager.asyncJsonStringByURL(API_ALL_AREA, new OkManager.CallbackResponse() {
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
                                customAreaAdapter = new CustomAreaAdapter(mContext, areaList);
                                listView.setAdapter(customAreaAdapter);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    WaitProgressDialog.closeDialog();
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (plantList == null) {
            manager.asyncJsonStringByURL(API_ALL_PLANT, new OkManager.CallbackResponse() {
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

                @Override
                public void onFailure(Call call, IOException e) {
                }
            });
        }
    }
}