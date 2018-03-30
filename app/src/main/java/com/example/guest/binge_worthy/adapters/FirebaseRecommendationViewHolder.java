package com.example.guest.binge_worthy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Recommendation;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

public class FirebaseRecommendationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final static String TAG = FirebaseRecommendationViewHolder.class.getSimpleName();
    private View mView;
    private Context mContext;
    private FirebaseUser user;
    ArrayList recommendations = new ArrayList();
    private static final int MAX_HEIGHT = 200;
    private static final int MAX_WIDTH = 200;


    public FirebaseRecommendationViewHolder (View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRecommendation(Recommendation recommendation) {
        //bind views
        ImageView mIconImageView = (ImageView) mView.findViewById(R.id.iconImageView);
        TextView mNameTextView = (TextView) mView.findViewById(R.id.nameTextView);
        //set values
        mNameTextView.setText(recommendation.getName());
        String path = "R.drawable." + recommendation.getType();
        int resId = getIcon(recommendation.getType());
        Log.d(TAG, path);
        Picasso.with(mContext)
                .load(resId)
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mIconImageView);
    }

    @Override
    public void onClick(View view) {

    }

    public int getIcon(String type) {
        if (type.equals("author") || type.equals("book")) {
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
