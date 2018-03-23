package com.example.guest.binge_worthy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.ui.DetailFragment;

import java.util.ArrayList;


public class RecommendationPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Recommendation> mRecommendations;


    public RecommendationPagerAdapter(FragmentManager fm, ArrayList<Recommendation> recommendations) {
        super(fm);
        mRecommendations = recommendations;
    }

    @Override
    public Fragment getItem(int position) {

        return DetailFragment.newInstance(mRecommendations.get(position));
    }


    @Override
    public int getCount() {
        return mRecommendations.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mRecommendations.get(position).getName();
    }
}
