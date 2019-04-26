package com.example.evaconnolly.electronicsstore.Helpers;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.evaconnolly.electronicsstore.Activities.MainActivity;
import com.example.evaconnolly.electronicsstore.Fragments.ViewItemFragment;
import com.example.evaconnolly.electronicsstore.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productImage, productTitle, productManufacturer, productPrice, productQuantity;
    String titleId, userId;
    private Context context;
    private FirebaseAuth mAuth;

    public ProductViewHolder(View itemView){
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        //productImage = (ImageView) itemView.findViewById(R.id.image);
        productTitle = (TextView) itemView.findViewById(R.id.title);
        productManufacturer = (TextView) itemView.findViewById(R.id.manufacturer);
        productPrice = (TextView) itemView.findViewById(R.id.price);
        productQuantity = (TextView) itemView.findViewById(R.id.quantity);

        titleId = productTitle.getText().toString();

    }

    @Override
    public void onClick(final View view) {
        System.out.println("made it to onclick");
        ViewItemFragment viewItemFragment = new ViewItemFragment();
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainContainer, viewItemFragment).commit();
        MainActivity.myBundle.putString("title", titleId);
    }





}
