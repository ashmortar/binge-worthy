package com.example.guest.binge_worthy.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.models.Movie;
import com.example.guest.binge_worthy.services.MovieService;
import com.example.guest.binge_worthy.ui.MovieDetailFragment;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class MoviePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Movie> mMovies;
    private Movie mMovie;

    public MoviePagerAdapter(FragmentManager fm, ArrayList<Movie> movies) {
        super(fm);
        mMovies = movies;
    }

    @Override
    public Fragment getItem(int position) {

        return MovieDetailFragment.newInstance(mMovies.get(position));
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMovies.get(position).getTitle();
    }

}
