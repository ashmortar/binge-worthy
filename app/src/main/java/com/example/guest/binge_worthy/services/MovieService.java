package com.example.guest.binge_worthy.services;


import android.util.Log;

import com.example.guest.binge_worthy.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MovieService {

    public static void movieListApiCall(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
