package com.hanan.and.udacity.popularmovies.data;

import android.net.Uri;

/**
 * Created by Nono on 2/22/2018.
 */

public final class FavouriteMoviesContract {
    /* Add content provider constants to the Contract
        1) Content authority,
        2) Base content URI,
        3) Path(s) to the tasks directory
        4) Content URI for data in the TaskEntry class
      */

    //the authority
    public static final String AUTHORITY = "com.hanan.and.udacity.popularmovies";

    //the base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "favourite" directory
    public static final String PATH_FAVOURITES = "favourites";


    private FavouriteMoviesContract() {
    }

    public static class FavouriteEntry {

        //FavouriteEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();

        //Favourites Table and column names
        public static final String TABLE_NAME = "favourites";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POSTER = "poster_path";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_VOTING = "vote_average";
        public static final String COLUMN_NAME_DATE = "release_date";
    }
}
