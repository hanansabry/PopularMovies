package com.hanan.and.udacity.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanan.and.udacity.popularmovies.adapter.MoviesAdapter;
import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nono on 2/19/2018.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.movie_title)
    TextView mTitleView;
    @BindView(R.id.user_ratings_value)
    TextView mUserRatingsView;
    @BindView(R.id.release_date_value)
    TextView mDateView;
    @BindView(R.id.overview_tv)
    TextView mOverviewTextView;
    @BindView(R.id.poster_imageView)
    ImageView mPosterImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        //hide action bar
        getSupportActionBar().hide();

        //get intent
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(MoviesAdapter.MOVIE);

        ButterKnife.bind(this);

        //fill views with data
        mTitleView.setText(movie.getOriginalTitle());
        mUserRatingsView.setText(movie.getVoteAverage());
        mDateView.setText(movie.getReleaseDate());
        mOverviewTextView.setText(movie.getOverview());
        Picasso.with(this)
                .load(Movie.POSTER_BASE_URL + Movie.SIZE_W185 + movie.getPosterPath())
                .placeholder(R.drawable.movie_place_holder)
                .error(R.drawable.error_loading_image)
                .into(mPosterImageView);
    }
}
