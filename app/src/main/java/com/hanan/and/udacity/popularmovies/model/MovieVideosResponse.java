package com.hanan.and.udacity.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nono on 2/21/2018.
 */

public class MovieVideosResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<MovieVideo> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieVideo> getResults() {
        return results;
    }

    public void setResults(List<MovieVideo> results) {
        this.results = results;
    }
}
