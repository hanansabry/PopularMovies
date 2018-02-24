package com.hanan.and.udacity.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.hanan.and.udacity.popularmovies.data.FavouriteMoviesContract.FavouriteEntry;

/**
 * Created by Nono on 2/22/2018.
 */

public class FavouriteMoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favourite_movies.db";
    private static final int DATABASE_VERSION = 1;

    public FavouriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold favourite movies
        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + FavouriteEntry.TABLE_NAME + " (" +
                FavouriteEntry._ID + " INTEGER PRIMARY KEY," +
                FavouriteEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_NAME_POSTER + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_NAME_OVERVIEW + " TEXT NOT NULL, " +
                FavouriteEntry.COLUMN_NAME_VOTING + " FLOAT NOT NULL, " +
                FavouriteEntry.COLUMN_NAME_DATE + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
