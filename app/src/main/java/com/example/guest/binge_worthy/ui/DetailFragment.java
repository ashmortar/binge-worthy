package com.example.guest.binge_worthy.ui;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Recommendation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.parceler.Parcels;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment  implements  View.OnClickListener {
    @BindView(R.id.fragNameView) TextView mFragNameView;
    @BindView(R.id.fragTeaserView) TextView mFragTeaserView;
    @BindView(R.id.fragTypeView) TextView mFragTypeView;
    @BindView(R.id.fragwUrlView) TextView mFragWUrlView;
    @BindView(R.id.fragyUrlView) TextView mFragYUrlView;
    @BindView(R.id.saveButton) Button mSaveButton;

    private Recommendation mRecommendation;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private String mSource;

    public DetailFragment() {}

    public static DetailFragment newInstance(Recommendation recommendation, String source) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.SOURCE_KEY, source);
        args.putParcelable("recommendation", Parcels.wrap(recommendation));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecommendation = Parcels.unwrap(getArguments().getParcelable("recommendation"));
            mSource = getArguments().getString(Constants.SOURCE_KEY);
            Log.i("onCreate", "ran");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        mFragNameView.setText(mRecommendation.getName());
        mFragTypeView.setText(mRecommendation.getType());
        mFragTeaserView.setText(mRecommendation.getwTeaser());
        mFragTeaserView.setMovementMethod(new ScrollingMovementMethod());
        mFragWUrlView.setText("wikipedia");
        mFragYUrlView.setText("youtube");

        mFragWUrlView.setOnClickListener(this);
        mFragYUrlView.setOnClickListener(this);
        if (mSource.equals(Constants.FROMFIREBASE)) {
            mSaveButton.setVisibility(View.GONE);
        } else {
            mSaveButton.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(buttonClick);
        if (v == mFragWUrlView) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRecommendation.getwUrl()));
            startActivity(webIntent);
        }
        if (v == mFragYUrlView) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRecommendation.getyUrl()));
            startActivity(webIntent);
        }
        if (v == mSaveButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference ref = FirebaseDatabase
                    .getInstance()
                    .getReference("users")
                    .child(uid);
            DatabaseReference instanceRef = ref.push();
            String pushId = instanceRef.getKey();
            mRecommendation.setPushId(pushId);
            instanceRef.setValue(mRecommendation);
            Toast.makeText(getContext(), "recommendation added to your list", Toast.LENGTH_SHORT).show();
        }
    }
}
