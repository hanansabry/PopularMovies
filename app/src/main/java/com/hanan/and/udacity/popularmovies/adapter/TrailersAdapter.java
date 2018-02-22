package com.hanan.and.udacity.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.model.MovieVideo;

import java.util.List;

/**
 * Created by Nono on 2/21/2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
    public static final String YOUTUBE_PATH = "https://www.youtube.com/watch?v=";
    List<MovieVideo> mTrailersList;
    Context mContext;

    public TrailersAdapter(Context context, List<MovieVideo> trailerList) {
        mTrailersList = trailerList;
        mContext = context;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.mTrailerTextView.setText(mTrailersList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mTrailersList.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTrailerTextView;
        int position;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            mTrailerTextView = itemView.findViewById(R.id.trailer_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            position = getAdapterPosition();
            MovieVideo trailer = mTrailersList.get(position);
            String trailerLink = YOUTUBE_PATH + trailer.getKey();
            //TODO open chooser to choose between apps to launch the trailer
            openTrailerVideo(trailerLink);
        }
    }

    private void openTrailerVideo(String link) {
        Uri webpage = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }
}
