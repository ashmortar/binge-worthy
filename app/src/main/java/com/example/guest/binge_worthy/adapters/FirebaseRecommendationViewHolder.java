package com.example.guest.binge_worthy.adapters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.ui.DetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import org.parceler.Parcels;
import java.util.ArrayList;

public class FirebaseRecommendationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final static String TAG = FirebaseRecommendationViewHolder.class.getSimpleName();
    private View mView;
    private Context mContext;
    private FirebaseUser user;
    private static final int MAX_HEIGHT = 200;
    private static final int MAX_WIDTH = 200;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    public ImageView mIconImageView;

    public FirebaseRecommendationViewHolder (View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRecommendation(Recommendation recommendation) {
        //bind views
        Log.d(TAG, "bindRecommendation: " + recommendation.getName());
        mIconImageView = (ImageView) mView.findViewById(R.id.iconImageView);
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
        view.startAnimation(buttonClick);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        final ArrayList<Recommendation> recommendations = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    recommendations.add(snapshot.getValue(Recommendation.class));
                }
                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(Constants.POSITION_KEY, itemPosition);
                intent.putExtra(Constants.RECSARRAY_KEY, Parcels.wrap(recommendations));
                intent.putExtra(Constants.SOURCE_KEY, Constants.FROMFIREBASE);
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getDetails());
            }
        });

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
