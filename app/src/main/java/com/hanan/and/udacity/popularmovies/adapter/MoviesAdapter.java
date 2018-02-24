package com.hanan.and.udacity.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.activity.DetailActivity;
import com.hanan.and.udacity.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nono on 2/18/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    public static final String MOVIE = "Movie";
    private final Context mContext;
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

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieThumb;
        int position;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieThumb = itemView.findViewById(R.id.movie_thumb);
            itemView.setOnClickListener(this);
        }

        public void bindMovieImagePoster() {
            position = getAdapterPosition();
            Movie movie = mMoviesList.get(position);
            String imagePath = Movie.POSTER_BASE_URL + Movie.SIZE_W185 + movie.getPosterPath();
            Picasso.with(mContext)
                    .load(imagePath)
                    .placeholder(R.drawable.movie_place_holder)
                    .error(R.drawable.error_loading_image)
                    .into(movieThumb);
        }

        @Override
        public void onClick(View view) {
            Movie movie = mMoviesList.get(position);
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(MOVIE, movie);
            mContext.startActivity(intent);
        }
    }

}
