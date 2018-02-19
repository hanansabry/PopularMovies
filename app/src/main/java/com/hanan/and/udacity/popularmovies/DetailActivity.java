package com.hanan.and.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanan.and.udacity.popularmovies.models.Movie;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get intent
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(MoviesAdapter.MOVIE);

        mPosterImageView = findViewById(R.id.poster_imageView);
        mTitleView = findViewById(R.id.movie_title);
        mUserRatingsView = findViewById(R.id.user_ratings_value);
        mDateView = findViewById(R.id.release_date_value);
        mOverviewTextView = findViewById(R.id.overview_tv);

        mTitleView.setText(movie.getOriginalTitle());
        mUserRatingsView.setText(movie.getUserRatings());
        mDateView.setText(movie.getReleaseDate());
        mOverviewTextView.setText(movie.getPlotSynopsis());
        Picasso.with(this).load(movie.getPosterImage()).into(mPosterImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
