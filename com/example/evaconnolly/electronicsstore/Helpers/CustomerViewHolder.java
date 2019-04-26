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

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView firstName, lastName, accountType, email, customerId, city;
    String titleId, userId;
    private Context context;
    private FirebaseAuth mAuth;

    public CustomerViewHolder(View itemView){
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        //productImage = (ImageView) itemView.findViewById(R.id.image);
        firstName = (TextView) itemView.findViewById(R.id.title);
        lastName = (TextView) itemView.findViewById(R.id.manufacturer);
        accountType = (TextView) itemView.findViewById(R.id.price);
        customerId = (TextView) itemView.findViewById(R.id.quantity);
        city = (TextView) itemView.findViewById(R.id.quantity);

        userId = customerId.getText().toString();

    }

    @Override
    public void onClick(final View view) {
        System.out.println("customer view holder printing....");
       // CustomerHistoryFragment customerHistoryFragment = new CustomerHistoryFragment();
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.mainContainer, customerHistoryFragment).commit();
        MainActivity.myBundle.putString("customerId", userId);
    }
}
