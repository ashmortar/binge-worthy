package com.example.guest.binge_worthy.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class ListActivity extends AppCompatActivity {
    public static final String TAG = ListActivity.class.getSimpleName();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerVew;
    private RecommendationListAdapter mAdapter;
    public ArrayList<Recommendation> recommendations = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

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

                ListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RecommendationListAdapter(getApplicationContext(), recommendations);
                        mRecyclerVew.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(ListActivity.this);
                        mRecyclerVew.setLayoutManager(layoutManager);
                        mRecyclerVew.setHasFixedSize(true);
                    }
                });
            }
        });

    }
}
