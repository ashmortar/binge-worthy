package com.example.guest.binge_worthy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.services.TasteDiveService;
import com.example.guest.binge_worthy.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.RecommendationViewHolder>{
    private static final String TAG = RecommendationListAdapter.class.getSimpleName();
    private Context mContext;
    private int itemPosition;
    private static final int MAX_HEIGHT = 200;
    private static final int MAX_WIDTH = 200;
    private ArrayList<Recommendation> mRecommendations = new ArrayList<>();

    public RecommendationListAdapter (Context context, ArrayList<Recommendation> recommendations) {
        mContext = context;
        mRecommendations = recommendations;
    }

    @Override
    public RecommendationListAdapter.RecommendationViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_list_item, parent, false);
        RecommendationViewHolder viewHolder = new RecommendationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecommendationListAdapter.RecommendationViewHolder holder, int position) {
        holder.bindRecommendation(mRecommendations.get(position));
    }
    @Override
    public int getItemCount() {
        return mRecommendations.size();
    }

    public class RecommendationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //bind views here
        @BindView(R.id.iconImageView)
        ImageView mIconImageView;
        @BindView(R.id.nameTextView)
        TextView mNameTextView;


        public RecommendationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("recommendations", Parcels.wrap(mRecommendations));
            mContext.startActivity(intent);
        }

        public void bindRecommendation(Recommendation recommendation) {
            //assign data to views
            String path = "R.drawable." + recommendation.getType();
            int resId = getIcon(recommendation.getType());
            Log.d(TAG, path);
            Picasso.with(mContext)
                    .load(resId)
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mIconImageView);
            mNameTextView.setText(recommendation.getName());
        }

        public int getIcon(String type) {
            if (type.equals("author")) {
                return R.drawable.author;
            }
            if (type.equals("movie")) {
                return R.drawable.movie;
            }
            if (type.equals("show")) {
                return R.drawable.show;
            }
            if (type.equals("music")) {
                return R.drawable.music;
            }
            if (type.equals("game")) {
                return R.drawable.game;
            }
            else {
                return R.drawable.photo;
            }
        }
    }
}
