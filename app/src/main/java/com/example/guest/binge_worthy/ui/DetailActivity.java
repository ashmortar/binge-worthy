package com.example.guest.binge_worthy.ui;
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

    private static final String TAG = DetailFragment.class.getSimpleName();

    ArrayList<Recommendation> mRecommendations = new ArrayList<>();
    int startingPosition;
    String source;
    Recommendation mRecommendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        source = getIntent().getStringExtra("source");
        startingPosition = getIntent().getIntExtra("position", 0);
        mRecommendations = Parcels.unwrap(getIntent().getParcelableExtra("recommendations"));
        mRecommendation = mRecommendations.get(startingPosition);
        DetailFragment frag = DetailFragment.newInstance(mRecommendation);
        FragmentTransaction ft = ((FragmentActivity) this).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detailsHere, frag);
        ft.commit();
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

}
