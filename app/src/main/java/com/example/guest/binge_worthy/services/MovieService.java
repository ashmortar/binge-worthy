package com.example.guest.binge_worthy.services;


import android.util.Log;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieService {

    public static void movieListApiCall(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Movie> processResults(Response response) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
        String jsonData = response.body().string();
        JSONObject moviedbJSON = new JSONObject(jsonData);
        JSONArray moviesJSON = moviedbJSON.getJSONArray("results");

        for (int i = 0; i < moviesJSON.length(); i++) {
            JSONObject movieJSON = moviesJSON.getJSONObject(i);
            int id = movieJSON.getInt("id");
            String title = movieJSON.getString("title");
            String overview = movieJSON.getString("overview");
            String posterUrl = movieJSON.getString("backdrop_path");
            String vote_average = movieJSON.getString("vote_average");
            String vote_count = movieJSON.getString("vote_count");
            Movie movie = new Movie(id, title, overview, posterUrl, vote_average, vote_count);
            movies.add(movie);
        }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
