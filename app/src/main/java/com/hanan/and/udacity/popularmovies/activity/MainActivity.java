package com.hanan.and.udacity.popularmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hanan.and.udacity.popularmovies.BuildConfig;
import com.hanan.and.udacity.popularmovies.adapter.MoviesAdapter;
import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.model.Movie;
import com.hanan.and.udacity.popularmovies.model.MovieResponse;
import com.hanan.and.udacity.popularmovies.model.MovieReviewsResponse;
import com.hanan.and.udacity.popularmovies.model.MovieVideo;
import com.hanan.and.udacity.popularmovies.model.MovieVideosResponse;
import com.hanan.and.udacity.popularmovies.model.UserReview;
import com.hanan.and.udacity.popularmovies.rest.ApiClient;
import com.hanan.and.udacity.popularmovies.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = BuildConfig.API_KEY;
    public static final int STATUS_CODE_OK = 200;
    public static final int STATUS_CODE_UNAUTHORIZED = 401;
    RecyclerView mMoviesRecyclerView;
    MoviesAdapter mMoviesAdapter;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);
        mMoviesRecyclerView = findViewById(R.id.movies_rv);
        //set the RecyclerView Layout
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mMoviesRecyclerView.setLayoutManager(mGridLayoutManager);

        //check the API KEY
        if (API_KEY.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.no_api_key_error), Toast.LENGTH_SHORT).show();
        }
        getApiResponse(R.id.popular_action);
//        getMovieReviewsResponse();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.popular_action) {
            setTitle(getResources().getString(R.string.popular) + " Movies");
        } else if (item.getItemId() == R.id.top_rated_action) {
            setTitle(getResources().getString(R.string.top_rated) + " Movies");
        }
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        getApiResponse(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void getApiResponse(int id) {

        /*  Resource : Android Working with Retrofit HTTP Library :
         *  Link     : https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
         */
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = null;
        if (id == R.id.popular_action) {
            call = apiService.getPopularMovies(API_KEY);
        } else if (id == R.id.top_rated_action) {
            call = apiService.getTopRatedMovies(API_KEY);
        }

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                int statusCode = response.code();
                if (statusCode == STATUS_CODE_OK) {
                    List<Movie> movies = response.body().getResults();
                    mMoviesAdapter = new MoviesAdapter(MainActivity.this, movies);
                    mMoviesRecyclerView.setAdapter(mMoviesAdapter);
                    mMoviesAdapter.notifyDataSetChanged();
                } else if (statusCode == STATUS_CODE_UNAUTHORIZED) {
                    Toast.makeText(MainActivity.this,
                            getApiErrorMsg(STATUS_CODE_UNAUTHORIZED), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            getGeneralError(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(MainActivity.this, getResources().getString(R.string.no_internet_connection),
                        Toast.LENGTH_LONG).show();
                Log.e("Movie", t.toString());
            }
        });
    }

    public String getApiErrorMsg(int errId) {
        if (errId == STATUS_CODE_UNAUTHORIZED) {
            return getResources().getString(R.string.wrong_api_key_error);
        } else {
            return getResources().getString(R.string.no_api_key_error);
        }
    }

    public String getGeneralError() {
        return getResources().getString(R.string.general_error);
    }


}
