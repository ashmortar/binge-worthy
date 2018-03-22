package com.example.guest.binge_worthy.services;


import android.util.Log;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.models.CastMember;
import com.example.guest.binge_worthy.models.CrewMember;
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

    public Movie processDetailResults(Response response, Movie thisMovie) {
        ArrayList<CastMember> castMembers = new ArrayList<>();
        ArrayList<CrewMember> crewMembers = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            JSONObject detailJSON = new JSONObject(jsonData);
            JSONObject credits = detailJSON.getJSONObject("credits");
            JSONArray castJSONArray = credits.getJSONArray("cast");
            JSONArray crewJSONArray = credits.getJSONArray("crew");

            for (int i = 0; i < 4; i++) {
                int cast_id = castJSONArray.getJSONObject(i).getInt("cast_id");
                String character = castJSONArray.getJSONObject(i).getString("character");
                String credit_id = castJSONArray.getJSONObject(i).getString("credit_id");
                int gender = castJSONArray.getJSONObject(i).getInt("gender");
                int id = castJSONArray.getJSONObject(i).getInt("id");
                String name = castJSONArray.getJSONObject(i).getString("name");
                String profile_path = castJSONArray.getJSONObject(i).getString("profile_path");
                CastMember castMember = new CastMember(cast_id, character, credit_id, gender, id, name, profile_path);
                castMembers.add(castMember);
            }
            for (int j = 0; j < 2; j++) {
                String credit_id = crewJSONArray.getJSONObject(j).getString("credit_id");
                String department = crewJSONArray.getJSONObject(j).getString("department");
                int gender = crewJSONArray.getJSONObject(j).getInt("gender");
                int id = crewJSONArray.getJSONObject(j).getInt("id");
                String job = crewJSONArray.getJSONObject(j).getString("job");
                String name = crewJSONArray.getJSONObject(j).getString("name");
                String profile_path = crewJSONArray.getJSONObject(j).getString("profile_path");
                CrewMember crewMember = new CrewMember(credit_id, department, gender, id, job, name, profile_path);
                crewMembers.add(crewMember);
            }
            thisMovie.setCast(castMembers);
            thisMovie.setCrew(crewMembers);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return thisMovie;
    }
}
