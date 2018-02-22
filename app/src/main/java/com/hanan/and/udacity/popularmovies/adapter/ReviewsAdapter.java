package com.hanan.and.udacity.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanan.and.udacity.popularmovies.R;
import com.hanan.and.udacity.popularmovies.model.UserReview;

import java.util.List;

/**
 * Created by Nono on 2/22/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.UserReviewViewHolder> {

    private Context mContext;
    private List<UserReview> mUserReviewList;

    public ReviewsAdapter(Context context, List<UserReview> userReviewList) {
        mContext = context;
        mUserReviewList = userReviewList;
    }

    @Override
    public UserReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_review_layout, parent, false);
        return new UserReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserReviewViewHolder holder, int position) {
        UserReview userReview = mUserReviewList.get(position);
        holder.mUserNameTextView.setText(userReview.getAuthor());
        holder.mUserReviewTextView.setText(userReview.getContent());
    }

    @Override
    public int getItemCount() {
        return mUserReviewList.size();
    }

    class UserReviewViewHolder extends RecyclerView.ViewHolder {
        TextView mUserNameTextView, mUserReviewTextView;

        public UserReviewViewHolder(View itemView) {
            super(itemView);
            mUserNameTextView = itemView.findViewById(R.id.user_name_textview);
            mUserReviewTextView = itemView.findViewById(R.id.user_review_textview);
        }
    }
}
