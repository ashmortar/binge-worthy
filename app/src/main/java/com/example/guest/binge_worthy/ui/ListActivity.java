package com.example.guest.binge_worthy.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.adapters.RecommendationListAdapter;
import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.services.TasteDiveService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = ListActivity.class.getSimpleName();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerVew;
    @BindView(R.id.textView2) TextView mSearchedTermView;
    private RecommendationListAdapter mAdapter;
    public ArrayList<Recommendation> recommendations = new ArrayList<>();
    Context mContext;
    Recommendation queryFound;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        mContext = this;

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        mSearchedTermView.setText(query);
        mSearchedTermView.setOnClickListener(this);

        getRecommendations(query);
    }

    private void getRecommendations(String query) {
        final TasteDiveService tasteDiveService = new TasteDiveService();

        tasteDiveService.apiCall(query, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v(TAG, response.toString());
                recommendations = tasteDiveService.processResults(response);


                if (recommendations.size() > 0 ) {
                    queryFound = recommendations.get(0);
                    recommendations.remove(0);
                    ListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new RecommendationListAdapter(getApplicationContext(), recommendations);
                            mRecyclerVew.setAdapter(mAdapter);
                            mRecyclerVew.setHasFixedSize(false);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            Log.d(TAG, "run: " + layoutManager.isAutoMeasureEnabled());
                            mRecyclerVew.setLayoutManager(layoutManager);
                        }
                    });
                } else {
                    ListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"your search did not match any results please try again", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ListActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        view.startAnimation(buttonClick);
        if (view == mSearchedTermView) {

            Toast.makeText(this, queryFound.getwTeaser(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: has run");
    }

}
