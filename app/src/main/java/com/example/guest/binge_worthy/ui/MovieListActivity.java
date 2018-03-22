package com.example.guest.binge_worthy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.adapters.MovieListAdapter;
import com.example.guest.binge_worthy.models.Movie;
import com.example.guest.binge_worthy.services.MovieService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class MovieListActivity extends AppCompatActivity {
    public static final String TAG = MovieListActivity.class.getSimpleName();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    public ArrayList<Movie> movies = new ArrayList<>();

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
                Log.v(TAG, e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v(TAG, response.toString());
                movies = movieService.processResults(response);

                MovieListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new MovieListAdapter(getApplicationContext(), movies);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(MovieListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
                for (int i =0; i < movies.size(); i++) {
                    final int position = i;
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MOVIE_DETAIL_URL + movies.get(i).getId()).newBuilder();
                    urlBuilder.addQueryParameter(Constants.API_KEY_PARAMETER, Constants.API_KEY);
                    urlBuilder.addQueryParameter(Constants.APPEND_TO_RESPONSE_PARAMETER, "credits");
                    String url = urlBuilder.build().toString();
                    movieService.movieListApiCall(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Movie detailMovie = movieService.processDetailResults(response, movies.get(position));
                            movies.set(position, detailMovie);
                        }
                    });
                }
            }
        });
    }
}
