package com.example.taipeizoo.observer;

import android.text.TextUtils;

import com.example.taipeizoo.model.Area;
import com.example.taipeizoo.webservice.OkManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static com.example.taipeizoo.webservice.OkManager.RESULT;
import static com.example.taipeizoo.webservice.OkManager.RESULTS;

public class UserDataRepository extends Observable {
    ArrayList<Area> areaList;
    private static UserDataRepository INSTANCE = null;

    private UserDataRepository() {
        getNewDataFromRemote();
    }

    private OkManager manager;

    private void getNewDataFromRemote() {
        manager = OkManager.getInstance();
        asyncGetAreaJsonData();
    }

    public static UserDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataRepository();
        }
        return INSTANCE;
    }

    public void setAreaListData(ArrayList<Area> mDataList) {
        areaList = mDataList;
        setChanged();
        notifyObservers();
    }

    public ArrayList<Area> getAreaListData() {
        return areaList;
    }

    private void asyncGetAreaJsonData() {
        if (manager != null) {
            manager.asyncJsonStringByURL(OkManager.API_ALL_AREA, result -> {
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
