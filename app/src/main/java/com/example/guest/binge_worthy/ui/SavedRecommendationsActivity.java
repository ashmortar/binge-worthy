package com.example.guest.binge_worthy.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.adapters.FirebaseRecommendationViewHolder;
import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.util.OnFirebaseDataChanged;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRecommendationsActivity extends AppCompatActivity implements OnFirebaseDataChanged{
    private static final String TAG = SavedRecommendationsActivity.class.getSimpleName();
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private FirebaseUser user;
    private Query query;
    private String uid;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.textView2) TextView mSearchedTermView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mSearchedTermView.setText("Saved Items");
        setupFirebaseAdapter();
    }

    private void setupFirebaseAdapter() {


        query = FirebaseDatabase.getInstance().getReference("users").child(uid);
        FirebaseRecyclerOptions<Recommendation> options = new FirebaseRecyclerOptions.Builder<Recommendation>()
                .setQuery(query, Recommendation.class)
                .build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Recommendation, FirebaseRecommendationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(FirebaseRecommendationViewHolder holder, int position, Recommendation model) {

                holder.bindRecommendation(model);
            }

            @NonNull
            @Override
            public FirebaseRecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_list_item, parent, false);
                return new FirebaseRecommendationViewHolder(view);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }

    @Override
    public void dataChanged() {
        mFirebaseAdapter.notifyDataSetChanged();
    }
}
