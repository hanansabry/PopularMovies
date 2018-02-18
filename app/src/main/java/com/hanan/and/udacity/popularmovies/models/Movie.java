package com.hanan.and.udacity.popularmovies.models;

/**
 * Created by Nono on 2/18/2018.
 */

public class Movie {
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String SIZE_W92 = "w92";
    private static final String SIZE_W154 = "w154";
    private static final String SIZE_W185 = "w185";
    private static final String SIZE_W342 = "w342";
    private static final String SIZE_W500 = "w500";
    private static final String SIZE_W780 = "w780";
    private static final String SIZE_ORIGINAL = "original";

    private String originalTitle;
    private String posterImage;
    private String plotSynopsis;
    private String userRatings;
    private String releaseDate;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

//    public String getPosterImageW92() {
//        return POSTER_BASE_URL + SIZE_W92 + posterImage;
//    }
//
//    public String getPosterImageW154() {
//        return POSTER_BASE_URL + SIZE_W154 + posterImage;
//    }
//
//    public String getPosterImageW185() {
//        return POSTER_BASE_URL + SIZE_W185 + posterImage;
//    }
//
//    public String getPosterImageW342() {
//        return POSTER_BASE_URL + SIZE_W342 + posterImage;
//    }
//
//    public String getPosterImageW500() {
//        return POSTER_BASE_URL + SIZE_W500 + posterImage;
//    }
//
//    public String getPosterImageW780() {
//        return POSTER_BASE_URL + SIZE_W780 + posterImage;
//    }
//
//    public String getPosterImageOriginal() {
//        return POSTER_BASE_URL + SIZE_ORIGINAL + posterImage;
//    }

    public String getPosterImage() { return posterImage; }

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
