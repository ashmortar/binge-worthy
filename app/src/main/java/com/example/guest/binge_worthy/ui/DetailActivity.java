package com.example.guest.binge_worthy.ui;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.adapters.RecommendationPagerAdapter;
import com.example.guest.binge_worthy.models.Recommendation;
import org.parceler.Parcels;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private static final String TAG = DetailFragment.class.getSimpleName();
    private RecommendationPagerAdapter adapterViewPager;
    ArrayList<Recommendation> mRecommendations = new ArrayList<>();
    int startingPosition;
    String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        source = getIntent().getStringExtra("source");
        startingPosition = getIntent().getIntExtra("position", 0);
        mRecommendations = Parcels.unwrap(getIntent().getParcelableExtra("recommendations"));

        adapterViewPager = new RecommendationPagerAdapter(getSupportFragmentManager(), mRecommendations);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition, true);
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
