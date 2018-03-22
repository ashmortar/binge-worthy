package com.example.guest.binge_worthy.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.adapters.MoviePagerAdapter;
import com.example.guest.binge_worthy.models.Movie;
import com.example.guest.binge_worthy.services.MovieService;

import org.parceler.Parcels;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private MoviePagerAdapter adapterViewPager;
    ArrayList<Movie> mMovies = new ArrayList<>();
    int startingPosition;
    Movie thisMovie;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        startingPosition = getIntent().getIntExtra("position", 0);
        mMovies = Parcels.unwrap(getIntent().getParcelableExtra("movies"));
        thisMovie = mMovies.get(startingPosition);

        adapterViewPager = new MoviePagerAdapter(getSupportFragmentManager(), mMovies);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }

}
