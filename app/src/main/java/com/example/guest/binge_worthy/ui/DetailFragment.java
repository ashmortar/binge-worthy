package com.example.guest.binge_worthy.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Recommendation;


import org.parceler.Parcels;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {
    @BindView(R.id.fragNameView)
    TextView mFragNameView;
    @BindView(R.id.fragTeaserView) TextView mFragTeaserView;
    @BindView(R.id.fragTypeView)
    TextView mFragTypeView;
    @BindView(R.id.fragwUrlView) TextView mFragWUrlView;
    @BindView(R.id.fragyUrlView) TextView mFragYUrlView;
    private Recommendation mRecommendation;

    public DetailFragment() {}

    public static DetailFragment newInstance(Recommendation recommendation) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("recommendation", Parcels.wrap(recommendation));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecommendation = Parcels.unwrap(getArguments().getParcelable("recommendation"));
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
        mFragWUrlView.setText(mRecommendation.getwUrl());
        mFragYUrlView.setText(mRecommendation.getyUrl());

        return view;
    }
}
