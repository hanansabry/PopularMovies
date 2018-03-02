package com.hanan.and.udacity.popularmovies.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hanan.and.udacity.popularmovies.BuildConfig;
import com.hanan.and.udacity.popularmovies.adapter.MoviesAdapter;
import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.data.FavouriteMoviesContract;
import com.hanan.and.udacity.popularmovies.data.FavouriteMoviesLoader;
import com.hanan.and.udacity.popularmovies.model.Movie;
import com.hanan.and.udacity.popularmovies.model.MovieResponse;
import com.hanan.and.udacity.popularmovies.model.MovieReviewsResponse;
import com.hanan.and.udacity.popularmovies.model.MovieVideo;
import com.hanan.and.udacity.popularmovies.model.MovieVideosResponse;
import com.hanan.and.udacity.popularmovies.model.UserReview;
import com.hanan.and.udacity.popularmovies.rest.ApiClient;
import com.hanan.and.udacity.popularmovies.rest.ApiInterface;
import com.hanan.and.udacity.popularmovies.data.FavouriteMoviesContract.FavouriteEntry;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = BuildConfig.API_KEY;
    public static final int STATUS_CODE_OK = 200;
    public static final int STATUS_CODE_UNAUTHORIZED = 401;
    public static final int SORT_BY_POPULAR = R.id.popular_action;
    public static final String SORT_CREATION_KEY = "SORT_CREATION_KEY";
    public static final String SORT_CREATION_FILE = "SORT_CREATION_FILE";
    public RecyclerView mMoviesRecyclerView;
    MoviesAdapter mMoviesAdapter;
    public ProgressBar mProgressBar;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);
        mMoviesRecyclerView = findViewById(R.id.movies_rv);
        //set the RecyclerView Layout
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, calculateNoOfColumns(this));
        mMoviesRecyclerView.setLayoutManager(mGridLayoutManager);

        //check the API KEY
        if (API_KEY.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.no_api_key_error), Toast.LENGTH_SHORT).show();
        }

        sharedPref = getSharedPreferences(SORT_CREATION_FILE, this.MODE_PRIVATE);
        int sortBy = sharedPref.getInt(SORT_CREATION_KEY, SORT_BY_POPULAR);
        Log.d("Sort By", "value of sorting : " + sortBy);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SORT_CREATION_KEY, sortBy);
        if (sortBy == R.id.my_favourite_action) {
            setTitle(getResources().getString(R.string.my_favourite));
            editor.putInt(SORT_CREATION_KEY, sortBy);
            FavouriteMoviesLoader loader = new FavouriteMoviesLoader(MainActivity.this, getLoaderManager());
        } else if (sortBy == R.id.popular_action){
            setTitle(getResources().getString(R.string.popular) + " Movies");
            getApiResponse(sortBy);
        } else if (sortBy == R.id.top_rated_action){
            setTitle(getResources().getString(R.string.top_rated) + " Movies");
            getApiResponse(sortBy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        sharedPref = getSharedPreferences(SORT_CREATION_FILE, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int itemId = item.getItemId();
        if (item.getItemId() == R.id.popular_action) {
            setTitle(getResources().getString(R.string.popular) + " Movies");
            editor.putInt(SORT_CREATION_KEY, itemId);
            getApiResponse(itemId);
        } else if (item.getItemId() == R.id.top_rated_action) {
            setTitle(getResources().getString(R.string.top_rated) + " Movies");
            editor.putInt(SORT_CREATION_KEY, itemId);
            getApiResponse(itemId);
        } else if (item.getItemId() == R.id.my_favourite_action) {
            setTitle(getResources().getString(R.string.my_favourite));
            editor.putInt(SORT_CREATION_KEY, itemId);
            FavouriteMoviesLoader loader = new FavouriteMoviesLoader(MainActivity.this, getLoaderManager());
        }
        editor.commit();
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        return super.onOptionsItemSelected(item);
    }

    /*  Resource : Android Working with Retrofit HTTP Library :
     *  Link     : https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
     */
    public void getApiResponse(int id) {
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

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

}
