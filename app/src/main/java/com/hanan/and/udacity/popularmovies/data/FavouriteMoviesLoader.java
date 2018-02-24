package com.hanan.and.udacity.popularmovies.data;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hanan.and.udacity.popularmovies.activity.MainActivity;
import com.hanan.and.udacity.popularmovies.adapter.MoviesAdapter;
import com.hanan.and.udacity.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nono on 2/24/2018.
 */

public class FavouriteMoviesLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    MainActivity mContext;
    public static final int FAVOURITE_LOADER_ID = 0;

    public FavouriteMoviesLoader(MainActivity context, LoaderManager loaderManager) {
        mContext = context;
        if (loaderManager.getLoader(FAVOURITE_LOADER_ID) == null) {
            loaderManager.initLoader(FAVOURITE_LOADER_ID, null, this);
        } else {
            loaderManager.restartLoader(FAVOURITE_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(mContext) {
            Cursor mData;

            @Override
            protected void onStartLoading() {
                if (mData != null) {
                    deliverResult(mData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return mContext.getContentResolver().query(FavouriteMoviesContract.FavouriteEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Movie> favourtieMovies = convertCurserResultToList(cursor);
        MoviesAdapter favouriteAdapter = new MoviesAdapter(mContext, favourtieMovies);
        mContext.mMoviesRecyclerView.setAdapter(favouriteAdapter);
        favouriteAdapter.notifyDataSetChanged();
        mContext.mProgressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader = null;
    }

    public List<Movie> convertCurserResultToList(Cursor cursor) {
        List<Movie> favouriteMovies = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry._ID);
            int titleIndex = cursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_NAME_TITLE);
            int posterIndex = cursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_NAME_POSTER);
            int overviewIndex = cursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_NAME_OVERVIEW);
            int voteIndex = cursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_NAME_VOTING);
            int dateIndex = cursor.getColumnIndex(FavouriteMoviesContract.FavouriteEntry.COLUMN_NAME_DATE);

            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            String poster = cursor.getString(posterIndex);
            String overview = cursor.getString(overviewIndex);
            float vote = cursor.getFloat(voteIndex);
            String date = cursor.getString(dateIndex);

            Movie movie = new Movie(id, title, poster, overview, String.valueOf(vote), date);
            favouriteMovies.add(movie);
        }
        return favouriteMovies;
    }
}
