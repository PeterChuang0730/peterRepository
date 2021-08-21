package com.example.taipeizoo.webservice;

import android.os.Handler;
import android.os.Looper;

import com.example.taipeizoo.view.WaitProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkManager {
    private final OkHttpClient client;
    private volatile static OkManager manager;
    private static OkManager instance = null;
    private final Handler handler;

    public static String API_ALL_AREA = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a";
    public static String API_ALL_PLANT = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14";

    public static String RESULT = "result";
    public static String RESULTS = "results";
    public static String AREADATA = "areaData";
    public static String PLANTLIST = "plantList";
    public static String PLANTDETAIL = "plantDetail";

    private OkManager() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkManager getInstance() {
        if (manager == null) {
            synchronized (OkManager.class) {
                if (instance == null) {
                    instance = new OkManager();
                    manager = instance;
                }
            }
        }
        return instance;
    }

    private void onSuccessJsonStringMethod(final String jsonValue, final CallbackResponse callBack) {
        handler.post(() -> {
            if (callBack != null) {
                try {
                    callBack.onResponse(jsonValue);
                } catch (Exception ignored) {

                }
            }
        });

    }

    public void asyncJsonStringByURL(String url, final CallbackResponse callback) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.post(() -> {
                    if (callback != null) {
                        try {
                            WaitProgressDialog.closeDialog();
                            WaitProgressDialog.noNetwork();
                        } catch (Exception ignored) {

                        }
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    try {
                        onSuccessJsonStringMethod(Objects.requireNonNull(response.body()).string(), callback);
                        response.close();
                    } catch (Exception ignored) {

                    }
                }
            }
        });

    }

    public interface CallbackResponse {
        void onResponse(String result);
    }
}