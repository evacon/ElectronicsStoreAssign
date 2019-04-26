package com.example.evaconnolly.electronicsstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaconnolly.electronicsstore.R;
import com.example.evaconnolly.electronicsstore.Helpers.ProductViewHolder;
import com.example.evaconnolly.electronicsstore.Objects.Product;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    public List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context){
        this.productList = productList;
        this.context = context;
    }

    public void updateList(List<Product> list){
        productList = list;
        notifyDataSetChanged();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_products_layout , null , false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ProductViewHolder productViewHolder = new ProductViewHolder(layoutView);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.productTitle.setText(productList.get(position).getTitle());
        if(productList.get(position).getTitle()!=null){
            holder.productTitle.setText(productList.get(position).getTitle());
        }
        if(productList.get(position).getManufacturer()!=null){
            holder.productManufacturer.setText(productList.get(position).getManufacturer());
        }
        if(productList.get(position).getPrice()!=null){
            holder.productPrice.setText(productList.get(position).getPrice());
        }
        if(productList.get(position).getTitle()!= null){
            holder.productQuantity.setText(productList.get(position).getQuantity());
        }
        if(productList.get(position).getImageUrl()!= null){
            holder.productImage.setText(productList.get(position).getImageUrl());
        }

    }
    @Override
    public int getItemCount() {
        return this.productList.size();
    }
}
