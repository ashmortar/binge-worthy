package com.example.guest.binge_worthy.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;
    @BindView(R.id.searchQuery) EditText mSearchQuery;
    @BindView(R.id.queryButton) Button mQueryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mQueryButton.setOnClickListener(this);
    }

    @
    Override
    public void onClick(View v) {

        if (v == mQueryButton) {
            String query = mSearchQuery.getText().toString();

            //validate input
            if (query.equals("")) {
                //tell user what sorts of searches can work
                Toast.makeText(this, "please enter a search query. \nTry a show, movie or band you like!", Toast.LENGTH_LONG).show();
            } else {
                //go to list activity
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
            }
        }
    }
}
