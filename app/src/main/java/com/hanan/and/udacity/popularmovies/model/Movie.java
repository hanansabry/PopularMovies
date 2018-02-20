package com.hanan.and.udacity.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nono on 2/18/2018.
 */

public class Movie implements Parcelable {
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String SIZE_W92 = "w92";
    public static final String SIZE_W154 = "w154";
    public static final String SIZE_W185 = "w185";
    public static final String SIZE_W342 = "w342";
    public static final String SIZE_W500 = "w500";
    public static final String SIZE_W780 = "w780";
    public static final String SIZE_ORIGINAL = "original";

    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie(String originalTitle, String posterPath, String overview,
                 String voteAverage, String releaseDate) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    //constructor used for parcel
    public Movie(Parcel parcel) {
        //read and set saved values from
        originalTitle = parcel.readString();
        posterPath = parcel.readString();
        overview = parcel.readString();
        voteAverage = parcel.readString();
        releaseDate = parcel.readString();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    //return the hashcode of the object
    @Override
    public int describeContents() {
        return hashCode();
    }

    //write object values to parcel for storage
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //write all properties to the parcel
        parcel.writeString(originalTitle);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseDate);
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[0];
        }
    };
}
