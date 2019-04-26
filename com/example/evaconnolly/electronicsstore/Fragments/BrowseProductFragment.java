package com.example.evaconnolly.electronicsstore.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.evaconnolly.electronicsstore.Objects.Product;
import com.example.evaconnolly.electronicsstore.Objects.ShoppingCart;
import com.example.evaconnolly.electronicsstore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BrowseProductFragment extends Fragment {

    private RecyclerView productList;
    private DatabaseReference ProductRef, RefineRef, cartRef;
    private Spinner mySpinner;
    private Button refineButton;
    private String refineSpinner;
    private Query query;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ImageButton cart;
    private static AdapterView.OnItemClickListener onItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_browse_product, container, false);
        productList = view.findViewById(R.id.update_product_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        productList.setLayoutManager(layoutManager);
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        mySpinner = (Spinner) view.findViewById(R.id.refineSpinner);
        refineButton = (Button) view.findViewById(R.id.refine);
       cart = (ImageButton) view.findViewById(R.id.imageButton);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        RefineRef = FirebaseDatabase.getInstance().getReference();

        Spinner dropdown = view.findViewById(R.id.refineSpinner);
        String[] refineBy = new String []{"All", "Earphones", "Headphones"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, refineBy);
        dropdown.setAdapter(adapter);
        refineSpinner = mySpinner.getSelectedItem().toString();
        query = RefineRef.child("Products").orderByChild("category").equalTo(refineSpinner);

        refineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                refineProducts();
            }
        });

      /* cart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                final String title = getRef(position).getKey();
                final String manufacturer = getRef(position).getKey();
                final String price = getRef(position).getKey();
                final String quantity = getRef(position).getKey();

                ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                Bundle bund = new Bundle();
                bund.putString("vk1", title);
                bund.putString("vk2", manufacturer);
                bund.putString("vk3", price);
                bund.putString("vk4", quantity);
                shoppingCartFragment.setArguments(bund);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.mainContainer, shoppingCartFragment).commit();
            }
        });*/

        DisplayProducts();

        return view;
    }

    private void refineProducts() {
        System.out.println("refineProducts();");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                System.out.println(refineSpinner);

                if(refineSpinner.equals("All")){
                    System.out.println("displaying all");
                    DisplayProducts();
                }
                else{
                    System.out.println("Displaying filtered");
                  //  FilterProducts();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

   /* private void FilterProducts() {FirebaseRecyclerOptions<Product> options =
            new FirebaseRecyclerOptions.Builder<Product>()
                    .setQuery(query, Product.class)
                    .build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model){
                System.out.println("made it to the onbindviewholder");
                holder.setTitle(model.getTitle());
                holder.setManufacturer(model.getManufacturer());
                holder.setPrice(model.getPrice());
                holder.setImageUrl(getActivity().getApplicationContext(), model.getImageUrl());

                final String title = getRef(position).getKey();
                final String manufacturer = getRef(position).getKey();
                final String price = getRef(position).getKey();
                final String quantity = getRef(position).getKey();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("made it to the onclick");
                        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                        Bundle bund = new Bundle();
                        bund.putString("vk1", title);
                        bund.putString("vk2", manufacturer);
                        bund.putString("vk3", price);
                        bund.putString("vk4", quantity);
                        shoppingCartFragment.setArguments(bund);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.mainContainer, shoppingCartFragment).commit();
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_products_layout, parent, false);
                return new ProductViewHolder(view);
            }
        };
        productList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }*/


    private void DisplayProducts() {FirebaseRecyclerOptions<Product> options =
            new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(ProductRef, Product.class)
                .build();

    FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options)
        {            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_products_layout, parent, false);
                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model){
                System.out.println("made it into the onbindviewholder in displayproducts");
                holder.setTitle(model.getTitle());
                holder.setManufacturer(model.getManufacturer());
                holder.setPrice(model.getPrice());
                holder.setImageUrl(getActivity().getApplicationContext(), model.getImageUrl());

                final String title = getRef(position).getKey();
                final String manufacturer = getRef(position).getKey();
                final String price = getRef(position).getKey();
                final String quantity = getRef(position).getKey();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("made it to the onclick");
                        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                        Bundle bund = new Bundle();
                        bund.putString("vk1", title);
                        bund.putString("vk2", manufacturer);
                        bund.putString("vk3", price);
                        bund.putString("vk4", quantity);
                        shoppingCartFragment.setArguments(bund);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.mainContainer, shoppingCartFragment).commit();
                    }
                });
            }
        };
        productList.setAdapter(firebaseRecyclerAdapter);
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

       /* public void setQuantity(String quantity){
            TextView productQuantity = (TextView) myView.findViewById(R.id.quantity);
            productQuantity.setText(quantity);
        }*/

       public void setImageUrl(Context context, String image){
            ImageView productImage = (ImageView) myView.findViewById(R.id.image);
            Picasso.with(context).load(image).into(productImage);
        }

    }
}