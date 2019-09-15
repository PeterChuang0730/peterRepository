package com.example.taipeizoo.webservice;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkManager {
    private OkHttpClient client;
    private volatile static OkManager manager;
    private static OkManager instance = null;
    //private final String TAG = OkManager.class.getSimpleName();
    private Handler handler;

    public static String RESULT = "result";
    public static String RESULTS = "results";
    public static String AREADATA = "areaData";
    public static String PLANTLIST = "plantList";
    public static String PLANTDETAIL = "plantDetail";

    private OkManager() {
        client = new OkHttpClient();
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
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(jsonValue);
                    } catch (Exception ignored) {

                    }
                }
            }
        });

    }

    public void asyncJsonStringByURL(String url, final CallbackResponse callback) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonStringMethod(response.body().string(), callback);
                }
            }
        });

    }

    public interface CallbackResponse {
        void onResponse(String result);

        void onFailure(Call call, IOException e);
    }
}