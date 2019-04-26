package com.example.evaconnolly.electronicsstore.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.evaconnolly.electronicsstore.Adapters.ProductAdapter;
import com.example.evaconnolly.electronicsstore.Objects.Product;
import com.example.evaconnolly.electronicsstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BrowseProductFragment extends Fragment {

    private EditText mSearch;
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private RecyclerView.LayoutManager productLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    Spinner spinner;
    private ArrayList<Product> productsList = new ArrayList<>();
    private ArrayList resultProducts = new ArrayList<Product>();
    private ArrayList<Product> getDataSetHistory() {
        return resultProducts;
    }
    private static final String[]order = {"ascending" , "descending"};
    String categorySpinner, downloadUrl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_browse_product, container, false);

        productRecyclerView = (RecyclerView)view.findViewById(R.id.update_product_view);
        productRecyclerView.setNestedScrollingEnabled(true);
        productRecyclerView.setHasFixedSize(true);

        productLayoutManager = new LinearLayoutManager(getActivity());
        productRecyclerView.setLayoutManager(productLayoutManager);
        productAdapter = new ProductAdapter(getDataSetHistory(), getActivity());
        productRecyclerView.setAdapter(productAdapter);

        dividerItemDecoration = new DividerItemDecoration(productRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        productRecyclerView.addItemDecoration(dividerItemDecoration);

        getProductIds();


        mSearch = (EditText)view.findViewById(R.id.search);
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });
        spinner = (Spinner)view.findViewById(R.id.orderSpinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, order);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.getSelectedItem().toString();

        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                Collections.sort(resultProducts, new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product t1) {
                        return product.getTitle().compareToIgnoreCase(t1.getTitle());
                    }
                    @Override
                    public Comparator<Product> reversed() {
                        return null;
                    }
                });
                productAdapter.notifyDataSetChanged();
                break;
            case 1:
                Collections.reverse(resultProducts);break;
        }
        productAdapter.notifyDataSetChanged();
    }

    private void getProductIds() {

        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        ProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot products : dataSnapshot.getChildren()){
                        getProductInfo(products.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void filter(String s) {

        List<Product> temp = new ArrayList<>();
        for(Product product : productsList){
            if(product.getTitle().toLowerCase().contains(s)){
                temp.add(product);
            }
            if(product.getTitle().toLowerCase().contains(s)){
                temp.add(product);
            }
        }
        productAdapter.updateList(temp);
    }

    private void getProductInfo(String key) {

        DatabaseReference ProductInfoRef = FirebaseDatabase.getInstance().getReference().child("Products").child(key);
        ProductInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String title = dataSnapshot.getKey().toString();
                    String manufacturer = "";
                    String price = "";
                    String quantity = "";
                    if(dataSnapshot.child("title").getValue() != null){
                        title = dataSnapshot.child("Title").getValue().toString();
                    }
                    if(dataSnapshot.child("manufacturer").getValue() != null){
                        manufacturer = dataSnapshot.child("Manufacturer").getValue().toString();
                    }
                    if(dataSnapshot.child("price").getValue() != null){
                        price = dataSnapshot.child("Price").getValue().toString();
                    }
                    if(dataSnapshot.child("quantity").getValue() != null){
                        price = dataSnapshot.child("Quantity").getValue().toString();
                    }

                    Product product = new Product(title, manufacturer, price, downloadUrl, quantity, categorySpinner);
                    resultProducts.add(product);
                    productsList.add(product);
                    productAdapter.notifyDataSetChanged();
                    System.out.println(resultProducts);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
