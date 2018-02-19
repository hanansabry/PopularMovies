package com.hanan.and.udacity.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nono on 2/18/2018.
 */

public class Movie implements Parcelable {

    private String originalTitle;
    private String posterImage;
    private String plotSynopsis;
    private String userRatings;
    private String releaseDate;

    public Movie(String originalTitle, String posterImage, String plotSynopsis,
                 String userRatings, String releaseDate) {
        this.originalTitle = originalTitle;
        this.posterImage = posterImage;
        this.plotSynopsis = plotSynopsis;
        this.userRatings = userRatings;
        this.releaseDate = releaseDate;
    }

    //constructor used for parcel
    public Movie(Parcel parcel) {
        //read and set saved values from
        originalTitle = parcel.readString();
        posterImage = parcel.readString();
        plotSynopsis = parcel.readString();
        userRatings = parcel.readString();
        releaseDate = parcel.readString();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(String userRatings) {
        this.userRatings = userRatings;
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
        parcel.writeString(posterImage);
        parcel.writeString(plotSynopsis);
        parcel.writeString(userRatings);
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
