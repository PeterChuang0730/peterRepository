package com.example.taipeizoo.mvvm;

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

import static com.example.taipeizoo.webservice.OkManager.RESULT;
import static com.example.taipeizoo.webservice.OkManager.RESULTS;

public class DataModel {

    public void retrieveData(final onDataReadyCallback callback) {
        OkManager manager = OkManager.getInstance();

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

                    callback.onDataReady(areaList);
                }
            }
        });
    }

    interface onDataReadyCallback {
        void onDataReady(ArrayList<Area> areaList);
    }
}
