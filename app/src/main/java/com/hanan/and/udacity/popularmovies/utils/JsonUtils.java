package com.hanan.and.udacity.popularmovies.utils;

import com.hanan.and.udacity.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nono on 2/19/2018.
 */

public class JsonUtils {

    public static List<Movie> parseMoviesListJson(String json) {
        List<Movie> movieList = new ArrayList<>();
        final String RESULTS = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";
        try {
            JSONObject movieListJsonObj = new JSONObject(json);

            //get moviesList
            JSONArray moviesArray = movieListJsonObj.getJSONArray(RESULTS);
            for (int i = 0; i < moviesArray.length(); i++){
                //get each movie details and assign them to movie Object
                String title = moviesArray.getJSONObject(i).getString(ORIGINAL_TITLE);
                String posterPath = moviesArray.getJSONObject(i).getString(POSTER_PATH);
                String overview = moviesArray.getJSONObject(i).getString(OVERVIEW);
                String voteAvg = moviesArray.getJSONObject(i).getString(VOTE_AVERAGE);
                String releaseDate = moviesArray.getJSONObject(i).getString(RELEASE_DATE);

                //create movie object
                Movie movie = new Movie(title, posterPath, overview, voteAvg, releaseDate);

                //add the movie object to the moviesList
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return movieList;
    }
}
