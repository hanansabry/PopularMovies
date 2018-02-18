package com.hanan.and.udacity.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hanan.and.udacity.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nono on 2/18/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String SIZE_W92 = "w92";
    private static final String SIZE_W154 = "w154";
    private static final String SIZE_W185 = "w185";
    private static final String SIZE_W342 = "w342";
    private static final String SIZE_W500 = "w500";
    private static final String SIZE_W780 = "w780";
    private static final String SIZE_ORIGINAL = "original";

    private Context mContext;
    private List<Movie> mMoviesList;

    public MoviesAdapter(Context context, List<Movie> movieList) {
        mContext = context;
        mMoviesList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindMovieImagePoster();
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieThumb;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieThumb = itemView.findViewById(R.id.movie_thumb);
        }

        public void bindMovieImagePoster() {
            int position = getAdapterPosition();
            Movie movie = mMoviesList.get(position);
            String imagePath = POSTER_BASE_URL + SIZE_W185 + movie.getPosterImage();
            Log.d("Movie", imagePath);
            Picasso.with(mContext).load(imagePath).into(movieThumb);
        }
    }
}