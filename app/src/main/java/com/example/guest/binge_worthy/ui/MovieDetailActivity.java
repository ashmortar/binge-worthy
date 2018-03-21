package com.example.guest.binge_worthy.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.guest.binge_worthy.R;

import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        Log.v(TAG, url);

    }
}
