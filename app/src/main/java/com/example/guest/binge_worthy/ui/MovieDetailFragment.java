package com.example.guest.binge_worthy.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Movie;
import com.example.guest.binge_worthy.services.MovieService;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailFragment extends Fragment {
    private static final int POSTER_MAX_WIDTH = 130;
    private static final int POSTER_MAX_HEIGHT = 190;
    private static final int HEADSHOT_MAX_WIDTH = 27;
    private static final int HEADSHOT_MAX_HEIGHT = 48;
    private Movie mMovie;

    //bind views here:
    @BindView(R.id.posterImageView)
    ImageView mPosterImageView;
    @BindView(R.id.fragmentTitleTextView)
    TextView mFragmentTitleTextView;
    @BindView(R.id.fragmentDescriptionTextView) TextView mFragmentDescriptionTextView;
    @BindView(R.id.fragmentVote_averageTextView) TextView mFragmentVote_averageTextView;
    @BindView(R.id.fragmentVote_countTextView) TextView mFragmentVote_countTextView;
    @BindView(R.id.castMember1ImageView) ImageView mCastMember1ImageView;
    @BindView(R.id.castMember1Character) TextView mCastMember1Character;
    @BindView(R.id.castMember1Name) TextView mCastMember1Name;
    @BindView(R.id.castMember2ImageView) ImageView mCastMember2ImageView;
    @BindView(R.id.castMember2Character) TextView mCastMember2Character;
    @BindView(R.id.castMember2Name) TextView mCastMember2Name;
    @BindView(R.id.castMember3ImageView) ImageView mCastMember3ImageView;
    @BindView(R.id.castMember3Character) TextView mCastMember3Character;
    @BindView(R.id.castMember3Name) TextView mCastMember3Name;
    @BindView(R.id.crewMemberImageView) ImageView mCrewMember1ImageView;
    @BindView(R.id.crewMember1Job) TextView mCrewMember1Job;
    @BindView(R.id.crewMember1Name) TextView mCrewMember1Name;

    public MovieDetailFragment() {}


    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", Parcels.wrap(movie));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieService movieService = new MovieService();
        if (getArguments() != null) {
            mMovie = Parcels.unwrap(getArguments().getParcelable("movie"));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);

        //assign data to views
        Picasso.with(view.getContext())
                .load(mMovie.getPosterUrl())
                .resize(POSTER_MAX_WIDTH, POSTER_MAX_HEIGHT)
                .centerCrop()
                .into(mPosterImageView);
        Picasso.with(view.getContext())
                .load(mMovie.getCast().get(0).getProfile_path())
                .resize(HEADSHOT_MAX_WIDTH, HEADSHOT_MAX_HEIGHT)
                .centerCrop()
                .into(mCastMember1ImageView);
        Picasso.with(view.getContext())
                .load(mMovie.getCast().get(1).getProfile_path())
                .resize(HEADSHOT_MAX_WIDTH, HEADSHOT_MAX_HEIGHT)
                .centerCrop()
                .into(mCastMember2ImageView);
        Picasso.with(view.getContext())
                .load(mMovie.getCast().get(2).getProfile_path())
                .resize(HEADSHOT_MAX_WIDTH, HEADSHOT_MAX_HEIGHT)
                .centerCrop()
                .into(mCastMember3ImageView);
        Picasso.with(view.getContext())
                .load(mMovie.getCrew().get(0).getProfile_path())
                .resize(HEADSHOT_MAX_WIDTH, HEADSHOT_MAX_HEIGHT)
                .centerCrop()
                .into(mCrewMember1ImageView);
        mCastMember1Character.setText(mMovie.getCast().get(0).getCharacter());
        mCastMember1Name.setText(mMovie.getCast().get(0).getName());
        mCastMember2Character.setText(mMovie.getCast().get(1).getCharacter());
        mCastMember2Name.setText(mMovie.getCast().get(1).getName());
        mCastMember3Character.setText(mMovie.getCast().get(2).getCharacter());
        mCastMember3Name.setText(mMovie.getCast().get(2).getName());
        mCrewMember1Job.setText(mMovie.getCrew().get(0).getJob());
        mCrewMember1Name.setText(mMovie.getCrew().get(0).getName());

        return view;
    }

}
