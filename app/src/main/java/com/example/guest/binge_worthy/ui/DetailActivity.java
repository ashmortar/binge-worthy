package com.example.guest.binge_worthy.ui;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.guest.binge_worthy.R;

import com.example.guest.binge_worthy.models.Recommendation;
import org.parceler.Parcels;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private int mOrientation;
    private static final String TAG = DetailFragment.class.getSimpleName();
    ArrayList<Recommendation> mRecommendations = new ArrayList<>();
    int startingPosition;
    String source;
    Recommendation mRecommendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mOrientation = getResources().getConfiguration().orientation;
        source = getIntent().getStringExtra("source");
        startingPosition = getIntent().getIntExtra("position", 0);
        mRecommendations = Parcels.unwrap(getIntent().getParcelableExtra("recommendations"));
        mRecommendation = mRecommendations.get(startingPosition);

        makeFrags(mRecommendation);

    }

    @Override
    public void onBackPressed() {
        if (source.equals("saved")) {
            Intent intent = new Intent(this, SavedRecommendationsActivity.class);
            startActivity(intent);
        }
        if (source.equals("api")) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }
    }

    private void makeFrags(Recommendation recommendation) {
        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
            DetailFragment dFrag = DetailFragment.newInstance(recommendation);
            FragmentTransaction dft = this.getSupportFragmentManager().beginTransaction();
            dft.replace(R.id.detailsHere, dFrag);
            dft.commit();
        }
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            YoutubeFragment yFrag = YoutubeFragment.newInstance(recommendation);
            FragmentTransaction yft = this.getSupportFragmentManager().beginTransaction();
            yft.replace(R.id.youtubeHere, yFrag);
            yft.commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            DetailFragment dFrag = DetailFragment.newInstance(mRecommendation);
            FragmentTransaction dft = this.getSupportFragmentManager().beginTransaction();
            dft.replace(R.id.detailsHere, dFrag);
            dft.commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            YoutubeFragment yFrag = YoutubeFragment.newInstance(mRecommendation);
            FragmentTransaction yft = this.getSupportFragmentManager().beginTransaction();
            yft.replace(R.id.youtubeHere, yFrag);
            yft.commit();
        }
    }


}
