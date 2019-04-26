package com.example.evaconnolly.electronicsstore.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evaconnolly.electronicsstore.R;
import com.google.firebase.auth.FirebaseAuth;

public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public ImageView mProductImage;
    public TextView mProductTitle, mDate, mRating, mComment, mFirstname, mLastname;

    public ReviewViewHolder(View itemView){
        super(itemView);
        itemView.setClickable(false);

        mProductImage = (ImageView) itemView.findViewById(R.id.image);
        mFirstname = (TextView) itemView.findViewById(R.id.firstname);
        mLastname= (TextView) itemView.findViewById(R.id.lastname);
        mRating = (TextView) itemView.findViewById(R.id.rating);
        mComment = (TextView) itemView.findViewById(R.id.comment);
        mProductTitle = (TextView) itemView.findViewById(R.id.profileImageText);
        mDate = (TextView)itemView.findViewById(R.id.date);
    }

    @Override
    public void onClick(View view) {

    }
}
