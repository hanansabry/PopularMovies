package com.hanan.and.udacity.popularmovies.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.hanan.and.udacity.popularmovies.adapter.MoviesAdapter;
import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.adapter.ReviewsAdapter;
import com.hanan.and.udacity.popularmovies.adapter.TrailersAdapter;
import com.hanan.and.udacity.popularmovies.model.Movie;
import com.hanan.and.udacity.popularmovies.model.MovieReviewsResponse;
import com.hanan.and.udacity.popularmovies.model.MovieVideo;
import com.hanan.and.udacity.popularmovies.model.MovieVideosResponse;
import com.hanan.and.udacity.popularmovies.model.UserReview;
import com.hanan.and.udacity.popularmovies.rest.ApiClient;
import com.hanan.and.udacity.popularmovies.rest.ApiInterface;
import com.hanan.and.udacity.popularmovies.data.FavouriteMoviesContract.FavouriteEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nono on 2/19/2018.
 */

public class DetailActivity extends AppCompatActivity {

    ExpandableRelativeLayout expandableLayout1;
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
    @BindView(R.id.trailers_rv)
    RecyclerView mTrailersRecyclerView;
    @BindView(R.id.reviews_rv)
    RecyclerView mUserReviewsRecycleView;

    TrailersAdapter mTrailersAdapter;
    ReviewsAdapter mReviewsAdapter;

    Movie movie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        //hide action bar
//        getSupportActionBar().hide();

        //allow Up navigation with the app icon in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.detail_activity_name));

        //get intent
        Intent intent = getIntent();
        movie = intent.getParcelableExtra(MoviesAdapter.MOVIE);

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

        Log.e("MovieID", movie.getId() + "");
        getMovieVideosResponse(movie.getId());
        getMovieReviewsResponse(movie.getId());

        //fill trailers recycler view
        LinearLayoutManager trailersLayout = new LinearLayoutManager(this);
        mTrailersRecyclerView.setLayoutManager(trailersLayout);

        //fill reviews recycler view
        LinearLayoutManager reviewsLayout = new LinearLayoutManager(this);
        mUserReviewsRecycleView.setLayoutManager(reviewsLayout);

        invalidateOptionsMenu();
    }

    public void getMovieVideosResponse(int movieId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieVideosResponse> call = apiService.getMovieVideos(movieId, MainActivity.API_KEY);

        call.enqueue(new Callback<MovieVideosResponse>() {
            @Override
            public void onResponse(Call<MovieVideosResponse> call, Response<MovieVideosResponse> response) {
                int statusCode = response.code();
                if (statusCode == MainActivity.STATUS_CODE_OK) {
                    List<MovieVideo> trailers = response.body().getResults();
                    mTrailersAdapter = new TrailersAdapter(DetailActivity.this, trailers);
                    mTrailersRecyclerView.setAdapter(mTrailersAdapter);
                    mTrailersAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieVideosResponse> call, Throwable t) {
                Log.e("Movie", t.toString());
            }
        });

    }

    public void getMovieReviewsResponse(int movieId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieReviewsResponse> call = apiService.getMovieReviews(movieId, MainActivity.API_KEY);

        call.enqueue(new Callback<MovieReviewsResponse>() {
            @Override
            public void onResponse(Call<MovieReviewsResponse> call, Response<MovieReviewsResponse> response) {
                int statusCode = response.code();
                if (statusCode == MainActivity.STATUS_CODE_OK) {
                    List<UserReview> reviews = response.body().getResults();
                    mReviewsAdapter = new ReviewsAdapter(DetailActivity.this, reviews);
                    mUserReviewsRecycleView.setAdapter(mReviewsAdapter);
                    mReviewsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieReviewsResponse> call, Throwable t) {
                Log.e("Movie", t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity_menu, menu);
        return true;
    }

    boolean isFavourite;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.add_favourite_action:
                if (isMovieFavourite()) {
                    isFavourite = false;
                    item.setIcon(android.R.drawable.star_big_off);
                    invalidateOptionsMenu();
                    deleteFromFavourites();
                    //delete from favourites
                } else {
                    isFavourite = true;
                    item.setIcon(android.R.drawable.star_big_on);
                    invalidateOptionsMenu();
                    addMovieToFavourites();
                }
                saveFavourtieValue(isFavourite);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isMovieFavourite()) {
            //in production you'd probably be better off keeping a reference to the item
            menu.findItem(R.id.add_favourite_action)
                    .setIcon(android.R.drawable.star_big_on);
        } else {
            menu.findItem(R.id.add_favourite_action)
                    .setIcon(android.R.drawable.star_big_off);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //method to add movie to the favourites database
    public void addMovieToFavourites() {
        // Insert new Movie data via a ContentResolver
        // Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();

        //put movie details into the contentValues
        contentValues.put(FavouriteEntry._ID, movie.getId());
        contentValues.put(FavouriteEntry.COLUMN_NAME_TITLE, movie.getOriginalTitle());
        contentValues.put(FavouriteEntry.COLUMN_NAME_POSTER, movie.getPosterPath());
        contentValues.put(FavouriteEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
        contentValues.put(FavouriteEntry.COLUMN_NAME_DATE, movie.getReleaseDate());
        contentValues.put(FavouriteEntry.COLUMN_NAME_VOTING, movie.getVoteAverage());

        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(FavouriteEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(this,
                    getResources().getString(R.string.added_to_favourite_msg), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.added_to_favourite_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFromFavourites() {
        Uri uri = FavouriteEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(String.valueOf(movie.getId())).build();

        // Delete a single row of data using a ContentResolver
        int deleted = getContentResolver().delete(uri, null, null);
        if (deleted > 0) {
            Toast.makeText(this,
                    getResources().getString(R.string.remove_from_favourite_msg), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveFavourtieValue(boolean favourite) {
        SharedPreferences sharedPref = this.getSharedPreferences(
                getResources().getString(R.string.preference_file_key), this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getResources().getString(R.string.favourite) + "_" + movie.getId()
                , favourite);
        editor.commit();
    }

    public boolean isMovieFavourite() {
        SharedPreferences sharedPref = getSharedPreferences(getResources().getString(R.string.preference_file_key),
                this.MODE_PRIVATE);
        boolean favourite = sharedPref.getBoolean(getResources().getString(R.string.favourite) + "_" + movie.getId(),
                getResources().getBoolean(R.bool.favourite));
        return favourite;
    }
}
