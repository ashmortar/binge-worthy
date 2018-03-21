package com.example.guest.binge_worthy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.services.MovieService;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        getMovies(url);
    }

    private void getMovies(String url) {
        final MovieService movieService = new MovieService();

        movieService.movieListApiCall(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
