package com.hanan.and.udacity.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.hanan.and.udacity.popularmovies.adapter.MoviesAdapter;
import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.model.Movie;
import com.hanan.and.udacity.popularmovies.model.MovieReviewsResponse;
import com.hanan.and.udacity.popularmovies.model.MovieVideo;
import com.hanan.and.udacity.popularmovies.model.MovieVideosResponse;
import com.hanan.and.udacity.popularmovies.model.UserReview;
import com.hanan.and.udacity.popularmovies.rest.ApiClient;
import com.hanan.and.udacity.popularmovies.rest.ApiInterface;
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
    @BindView(R.id.trailers_content_tv)
    TextView mMovieVideos;
    @BindView(R.id.reviews_content_tv)
    TextView mMovieReviews;

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

        Log.e("MovieID", movie.getId()+"");
        getMovieVideosResponse(movie.getId());
        getMovieReviewsResponse(movie.getId());
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
                    StringBuilder trailersContent = new StringBuilder();
                    for (MovieVideo trailer : trailers) {
                        trailersContent.append(trailer.getName()).append("\n");
                    }
                    mMovieVideos.setText(trailersContent);
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
                    StringBuilder userReviewContent = new StringBuilder();
                    for (UserReview review : reviews) {
                        userReviewContent.append(review.getAuthor()).append("\n")
                                .append(review.getContent())
                                .append("\n\n");
                    }
                    mMovieReviews.setText(userReviewContent);
                }
            }

            @Override
            public void onFailure(Call<MovieReviewsResponse> call, Throwable t) {
                Log.e("Movie", t.toString());
            }
        });
    }

}
