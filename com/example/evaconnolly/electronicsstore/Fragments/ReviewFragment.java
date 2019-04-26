package com.example.evaconnolly.electronicsstore.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.evaconnolly.electronicsstore.Adapters.ReviewAdapter;
import com.example.evaconnolly.electronicsstore.Objects.Review;
import com.example.evaconnolly.electronicsstore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ReviewFragment extends Fragment {

    String title; //id for product
    TextView mRating;
    Button mConfirm;
    RatingBar mRatingBar;
    EditText mComment;
    String rating, comment, userId, profileImageUrl, firstname, lastname, date, productTitle;
    FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private ReviewAdapter mAdapter;
    private RecyclerView.LayoutManager mReviewLayout;
    DividerItemDecoration dividerItemDecoration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mComment = (EditText) view.findViewById(R.id.comment);
        mConfirm = (Button) view.findViewById(R.id.confirm);
        mRating = (TextView) view.findViewById(R.id.Rate);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.reviews);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setHasFixedSize(true);

        mReviewLayout = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mReviewLayout);
        mAdapter = new ReviewAdapter(getDataSetHistory(), getActivity());
        mRecyclerView.setAdapter(mAdapter);

        dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProductRating(title);
                BrowseProductFragment browseProductFragment = new BrowseProductFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.mainContainer, browseProductFragment).commit();
            }
        });

        getUserInfo();

        getRatingIds();
        return view;
    }

    private void getUserInfo() {
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("firstname").getValue().toString() != null) {
                        firstname = dataSnapshot.child("firstname").getValue().toString();
                    }
                    if (dataSnapshot.child("lastname").getValue().toString() != null) {
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                    }
                    if (dataSnapshot.child("profileImageUrl").getValue() != null) {
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getRatingIds() {
        DatabaseReference RatingsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(title).child("rating");
        RatingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot reviews : dataSnapshot.getChildren()) {
                        getReviewInfo(reviews.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getReviewInfo(String key) {
        DatabaseReference reviewinfo = FirebaseDatabase.getInstance().getReference().child("Products").child(title).child("rating").child(key);
        reviewinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    firstname = "";
                    lastname = "";
                    comment = "";
                    rating = "";
                    date = "";
                    profileImageUrl = "";
                    if (dataSnapshot.child("firstname").getValue().toString() != null) {
                        firstname = dataSnapshot.child("firstname").getValue().toString();
                    }
                    if (dataSnapshot.child("lastname").getValue().toString() != null) {
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                    }
                    if (dataSnapshot.child("comment").getValue().toString() != null) {
                        comment = dataSnapshot.child("comment").getValue().toString();
                    }
                    if (dataSnapshot.child("rating").getValue().toString() != null) {
                        rating = dataSnapshot.child("rating").getValue().toString();
                    }
                    if (dataSnapshot.child("date").getValue().toString() != null) {
                        date = dataSnapshot.child("date").getValue().toString();
                    }
                    if (dataSnapshot.child("profileUrl").getValue() != null) {
                        profileImageUrl = dataSnapshot.child("profileUrl").getValue().toString();
                    }

                    Review review = new Review(date, comment, firstname, lastname, rating, profileImageUrl);
                    resultReview.add(review);
                    reviewList.add(review);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveProductRating(final String title) {

        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("Products").child(title);
        ProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    productTitle = dataSnapshot.child("Title").getValue().toString();

                    mRating.setText("Rate the product: " + productTitle);

                    Date todaysdate = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    date = format.format(todaysdate);
                    DatabaseReference ratingsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(title).child("rating");

                    rating = String.valueOf(mRatingBar.getRating());
                    comment = mComment.getText().toString();

                    HashMap ratings = new HashMap();
                    ratings.put("customerId", userId);
                    ratings.put("rating", rating);
                    ratings.put("comment", comment);
                    ratings.put("date", date);
                    ratings.put("firstname", firstname);
                    ratings.put("lastname", lastname);
                    ratings.put("profileUrl", profileImageUrl);

                    ratingsRef.push().setValue(ratings);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<Review> reviewList = new ArrayList<>();


    private ArrayList resultReview = new ArrayList<Review>();

    private ArrayList<Review> getDataSetHistory() {
        return resultReview;
    }
}
