package com.example.guest.binge_worthy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;
    @BindView(R.id.searchQuery) EditText mSearchQuery;
    @BindView(R.id.releaseDateQuery) EditText mReleaseDateQuery;
    @BindView(R.id.releaseDateSearchButton) Button mReleaseDateSearchButton;
    @BindView(R.id.queryButton) Button mQueryButton;
    @BindView(R.id.ratingSearchButton) Button mRatingSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRatingSearchButton.setOnClickListener(this);
        mQueryButton.setOnClickListener(this);
        mReleaseDateSearchButton.setOnClickListener(this);
    }

    @
    Override
    public void onClick(View v) {
        if (v == mRatingSearchButton) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.RATING_SEARCH_URL).newBuilder();
            urlBuilder.addQueryParameter(Constants.API_KEY_PARAMETER, Constants.API_KEY);
            String url = urlBuilder.build().toString();
            Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }


    }
}
