package com.hanan.and.udacity.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanan.and.udacity.popularmovies.adapter.MoviesAdapter;
import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Nono on 2/19/2018.
 */

public class DetailActivity extends AppCompatActivity {

    TextView mTitleView, mUserRatingsView, mDateView, mOverviewTextView;
    ImageView mPosterImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        //allow Up navigation with the app icon in the action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().hide();
        //get intent
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(MoviesAdapter.MOVIE);

        mPosterImageView = findViewById(R.id.poster_imageView);
        mTitleView = findViewById(R.id.movie_title);
        mUserRatingsView = findViewById(R.id.user_ratings_value);
        mDateView = findViewById(R.id.release_date_value);
        mOverviewTextView = findViewById(R.id.overview_tv);

        //fill views with data
        mTitleView.setText(movie.getOriginalTitle());
        mUserRatingsView.setText(movie.getVoteAverage());
        mDateView.setText(movie.getReleaseDate());
        mOverviewTextView.setText(movie.getOverview());
        //TODO change placeholder image and error image
        Picasso.with(this)
                .load(Movie.POSTER_BASE_URL + Movie.SIZE_W185 + movie.getPosterPath())
                .placeholder(R.drawable.movie_place_holder)
                .error(android.R.drawable.stat_notify_error)
                .into(mPosterImageView);
    }
}
