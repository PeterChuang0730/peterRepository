package com.example.taipeizoo.observer;

import android.text.TextUtils;

import com.example.taipeizoo.model.Area;
import com.example.taipeizoo.view.WaitProgressDialog;
import com.example.taipeizoo.webservice.OkManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.taipeizoo.webservice.OkManager.RESULT;
import static com.example.taipeizoo.webservice.OkManager.RESULTS;

public class UserDataRepository implements Subject {
    ArrayList<Area> areaList;
    private static UserDataRepository INSTANCE = null;

    private final ArrayList<RepositoryObserver> mObservers;

    private UserDataRepository() {
        mObservers = new ArrayList<>();
        getNewDataFromRemote();
    }

    private OkManager manager;

    private void getNewDataFromRemote() {
        manager = OkManager.getInstance();
        getAreaJsonData();

    }

    public static UserDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataRepository();
        }
        return INSTANCE;
    }

    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if (!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        mObservers.remove(repositoryObserver);
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver observer : mObservers) {
            observer.onAreaListDataChanged(areaList);
        }
    }

    public void setAreaListData(ArrayList<Area> mDataList) {
        areaList = mDataList;
        notifyObservers();
    }

    private void getAreaJsonData() {
        if (manager != null) {
            manager.asyncJsonStringByURL(OkManager.API_ALL_AREA, result -> {
                WaitProgressDialog.closeDialog();

                if (!TextUtils.equals(result, "")) {
                    Gson gson = new Gson();
                    JsonObject jo = gson.fromJson(result, JsonObject.class);
                    JsonObject jsonResult = jo.getAsJsonObject(RESULT);
                    JsonArray arrayResults = jsonResult.getAsJsonArray(RESULTS);

                    if (arrayResults != null) {
                        Type collectionType = new TypeToken<List<Area>>() {
                        }.getType();
                        ArrayList<Area> areaList = gson.fromJson(arrayResults, collectionType);

                        setAreaListData(areaList);
                    }
                }
            });
        }
    }
}
