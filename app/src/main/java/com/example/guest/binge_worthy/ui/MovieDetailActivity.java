package com.example.guest.binge_worthy.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Movie;
import com.example.guest.binge_worthy.services.MovieService;

import org.parceler.Parcels;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    ArrayList<Movie> mMovies = new ArrayList<>();
    int startingPosition;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        startingPosition = getIntent().getIntExtra("position", 0);
        mMovies = Parcels.unwrap(getIntent().getParcelableExtra("movies"));
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MOVIE_DETAIL_URL + mMovies.get(startingPosition).getId()).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_KEY_PARAMETER, Constants.API_KEY);
        urlBuilder.addQueryParameter(Constants.APPEND_TO_RESPONSE_PARAMETER, "credits");
        String url = urlBuilder.build().toString();

        getMovieDetail(url);

    }

    private void getMovieDetail(String url) {
        final MovieService movieService = new MovieService();

        movieService.movieListApiCall(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMovies = movieService.processDetailResults(response, mMovies, startingPosition);
            }
        });
    }
}
