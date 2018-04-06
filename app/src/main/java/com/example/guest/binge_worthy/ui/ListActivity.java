package com.example.guest.binge_worthy.ui;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Recommendation;
import com.example.guest.binge_worthy.util.OnSelectedListener;
import org.parceler.Parcels;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements OnSelectedListener {
    public static final String TAG = ListActivity.class.getSimpleName();
    private Integer mPos;
    private ArrayList<Recommendation> mRecs;
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState != null) {
            mPos = savedInstanceState.getInt(Constants.POSITION_KEY);
            mRecs = Parcels.unwrap(savedInstanceState.getParcelable(Constants.RECSARRAY_KEY));
            mSource = savedInstanceState.getString(Constants.SOURCE_KEY);

            if (mPos != null && mRecs != null && mSource != null) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra(Constants.POSITION_KEY, mPos);
                intent.putExtra(Constants.RECSARRAY_KEY, mRecs);
                intent.putExtra(Constants.SOURCE_KEY, mSource);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(Integer position, ArrayList<Recommendation> recommendations, String source) {
        mPos = position;
        mRecs = recommendations;
        mSource = source;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPos != null && mRecs != null && mSource != null) {
            outState.putInt(Constants.POSITION_KEY, mPos);
            outState.putString(Constants.SOURCE_KEY, mSource);
            outState.putParcelable(Constants.RECSARRAY_KEY, Parcels.wrap(mRecs));
        }
    }
}
