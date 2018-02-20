package com.hanan.and.udacity.popularmovies.rest;

import com.hanan.and.udacity.popularmovies.model.Movie;
import com.hanan.and.udacity.popularmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nono on 2/20/2018.
 */

public interface ApiInterface {
    @GET("top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);
}
