package com.example.guest.binge_worthy.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.binge_worthy.Constants;
import com.example.guest.binge_worthy.R;
import com.example.guest.binge_worthy.models.Movie;
import com.example.guest.binge_worthy.ui.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private static final String TAG = MovieListAdapter.class.getSimpleName();
    private static final int MAX_WIDTH = 130;
    private static final int MAX_HEIGHT = 190;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    public MovieListAdapter(Context context, ArrayList<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public MovieListAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.MovieViewHolder holder, int position)  {
        holder.bindMovie(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.moviePosterView) ImageView mMoviePosterView;
        @BindView(R.id.titleTextView) TextView mTitleTextView;
        @BindView(R.id.descriptionTextView) TextView mDescriptionTextView;
        @BindView(R.id.vote_averageTextView) TextView mVote_AverageTextView;
        @BindView(R.id.vote_countTextView) TextView mVote_CountTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, MovieDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("restaurants", Parcels.wrap(mMovies));

            mContext.startActivity(intent);

        }

        public void bindMovie(Movie movie) {
            Log.v(TAG, movie.getTitle());
            Picasso.with(mContext)
                    .load(movie.getPosterUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mMoviePosterView);
            mTitleTextView.setText(movie.getTitle());
            mDescriptionTextView.setText(movie.getOverview());
            mVote_AverageTextView.setText(movie.getVote_average());
            mVote_CountTextView.setText(movie.getVote_count());
        }
    }
}
