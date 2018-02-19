package com.hanan.and.udacity.popularmovies.models;

/**
 * Created by Nono on 2/18/2018.
 */

public class Movie {

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

}
