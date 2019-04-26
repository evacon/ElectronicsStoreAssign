package com.example.evaconnolly.electronicsstore.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.evaconnolly.electronicsstore.Helpers.ReviewViewHolder;
import com.example.evaconnolly.electronicsstore.Objects.Review;
import com.example.evaconnolly.electronicsstore.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    public List<Review> reviewList;
    private Context context;

    public ReviewAdapter(List<Review> reviewList, Context context){
        this.reviewList = reviewList;
        this.context = context;
    }

    public void updateList(List<Review> list){
        reviewList = list;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout , null , false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(layoutView);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, final int position) {
        if(reviewList.get(position).getFirstname()!=null){
            holder.mFirstname.setText(reviewList.get(position).getFirstname());
        }
        if(reviewList.get(position).getLastname()!=null){
            holder.mLastname.setText(reviewList.get(position).getLastname());
        }
        if(reviewList.get(position).getDate()!=null){
            holder.mDate.setText(reviewList.get(position).getDate());
        }
        if(reviewList.get(position).getRating()!=null){
            holder.mRating.setText(reviewList.get(position).getRating());
        }
        if(reviewList.get(position).getComment()!=null){
            holder.mComment.setText(reviewList.get(position).getComment());
        }
        if(reviewList.get(position).getProfileImageUrl()!= null){
            holder.mProductTitle.setText(reviewList.get(position).getProfileImageUrl());
            //Glide.with(context).load(reviewList.get(position).getProfileImageUrl()).into(holder.mProductTitle);
        }

    }

    @Override
    public int getItemCount() {
        return this.reviewList.size();
    }
}
