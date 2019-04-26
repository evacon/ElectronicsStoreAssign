package com.example.evaconnolly.electronicsstore.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaconnolly.electronicsstore.Objects.Product;
import com.example.evaconnolly.electronicsstore.Objects.ShoppingCart;
import com.example.evaconnolly.electronicsstore.ProductAdapter;
import com.example.evaconnolly.electronicsstore.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {
    private Button checkout;
    private TextView total, totalPrice;
    private List<Product> productListItems = new ArrayList<>();
    DatabaseReference shoppingCartRef;
    private ListView shoppingCartList;
    private ProductAdapter adapter;
    String currentUser, productTotal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        shoppingCartList = view.findViewById(R.id.shoppingCartList);
        checkout = (Button) view.findViewById(R.id.checkout);
        shoppingCartList = (ListView) view.findViewById(R.id.shoppingCartList);
        total = (TextView) view.findViewById(R.id.total);
        totalPrice = (TextView) view.findViewById(R.id.totalPrice);

        final String productTitle = getArguments().getString("vk1");
        final String productManufacturer = getArguments().getString("vk2");
        final String productPrice = getArguments().getString("vk3");
        final String productQuntity = getArguments().getString("vk4");

        addToCart();

        shoppingCartRef = FirebaseDatabase.getInstance().getReference().child("ShoppingCart");

        checkout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                ShoppingCart shoppingCart = new ShoppingCart(productTitle, productManufacturer, productPrice, productQuntity, productTotal);
                String cartId = shoppingCartRef.push().getKey();

                shoppingCartRef.child("ShoppingCart").child(currentUser).setValue(shoppingCart).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Added to the cart", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        return view;
    }

    private void addToCart() {FirebaseRecyclerOptions<Product> options =
            new FirebaseRecyclerOptions.Builder<Product>()
                    .setQuery(shoppingCartRef, Product.class)
                    .build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model){
                holder.setTitle(model.getTitle());
                holder.setManufacturer(model.getManufacturer());
                holder.setPrice(model.getPrice());
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_products_layout, parent, false);
                return new ProductViewHolder(view);
            }
        };
        shoppingCartList.setAdapter((ListAdapter) firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        View myView;

        public ProductViewHolder(final View itemView)
        {
            super(itemView);
            myView = itemView;
        }

        public void setTitle(String title){
            TextView productTitle = (TextView) myView.findViewById(R.id.title);
            productTitle.setText(title);
        }

        public void setManufacturer(String manufacturer){
            TextView productManufacturer = (TextView) myView.findViewById(R.id.manufacturer);
            productManufacturer.setText(manufacturer);
        }

        public void setPrice(String price){
            TextView productPrice = (TextView) myView.findViewById(R.id.price);
            productPrice.setText(price);
        }

    }
}
