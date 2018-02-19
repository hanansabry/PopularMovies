package com.hanan.and.udacity.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.hanan.and.udacity.popularmovies.models.Movie;
import com.hanan.and.udacity.popularmovies.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mMoviesRecyclerView;
    List<Movie> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesRecyclerView = findViewById(R.id.movies_rv);
        //set the RecyclerView Layout
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mMoviesRecyclerView.setLayoutManager(mGridLayoutManager);

        populateMoviesList();

        //set the RecyclerView Adapter
        MoviesAdapter mMoviesAdapter = new MoviesAdapter(MainActivity.this, mMoviesList);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    public void populateMoviesList() {
        String json = getResources().getString(R.string.movie_example_data);
        mMoviesList = JsonUtils.parseMoviesListJson(json);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.top_rated_action) {
            Toast.makeText(this, "Show top_rated movies", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
