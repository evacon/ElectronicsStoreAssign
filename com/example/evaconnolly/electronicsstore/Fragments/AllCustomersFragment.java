package com.example.evaconnolly.electronicsstore.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaconnolly.electronicsstore.Adapters.CustomerAdapter;
import com.example.evaconnolly.electronicsstore.Objects.Customer;
import com.example.evaconnolly.electronicsstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllCustomersFragment extends Fragment {

    private RecyclerView custRecyclerView;
    private CustomerAdapter custAdapter;
    private RecyclerView.LayoutManager customerLayout;
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList customerResults = new ArrayList<Customer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_customers, container, false);

        custRecyclerView = (RecyclerView) view.findViewById(R.id.customers);
        custRecyclerView.setNestedScrollingEnabled(true);
        custRecyclerView.setHasFixedSize(true);

        customerLayout = new LinearLayoutManager(getActivity());
        custRecyclerView.setLayoutManager(customerLayout);
        //custAdapter = new CustomerAdapter(getDataSetHistory(), getActivity());
        custRecyclerView.setAdapter(custAdapter);

        getCustomerTitle();

        return view;
    }

    private void getCustomerTitle() {

        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("Users");
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot customers : dataSnapshot.getChildren()){
                        getCustomerInfo(customers.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCustomerInfo(String key){
        DatabaseReference customerInfoRef = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        customerInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String customerId = dataSnapshot.getKey();
                    String firstname = "";
                    String lastname = "";
                    String email = "";
                    String accountType= "";
                    String city = "";
                    String buildingNumber = "";
                    String streetName = "";

                    if(dataSnapshot.child("firstname").getValue() != null){
                        firstname = dataSnapshot.child("firstname").getValue().toString();
                    }
                    if(dataSnapshot.child("lastname").getValue() != null){
                        lastname = dataSnapshot.child("lastname").getValue().toString();
                    }
                    if(dataSnapshot.child("email").getValue() != null){
                        email = dataSnapshot.child("email").getValue().toString();
                    }

                    Customer customer = new Customer(firstname, lastname, email, customerId, accountType, buildingNumber, city, streetName);
                    customerResults.add(customer);
                    customerList.add(customer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private ArrayList<Customer> getDataSetHistory() {
        return customerResults;
    }

}
