package com.example.evaconnolly.electronicsstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaconnolly.electronicsstore.Helpers.CustomerViewHolder;
import com.example.evaconnolly.electronicsstore.Objects.Product;
import com.example.evaconnolly.electronicsstore.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {

    public List<Product> customerList;
    private Context context;

    public CustomerAdapter(List<Product> customerList, Context context){
        this.customerList = customerList;
        this.context = context;
    }

    public void updateList(List<Product> list){
        customerList = list;
        notifyDataSetChanged();
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_info , null , false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(layoutParams);
        CustomerViewHolder customerViewHolder = new CustomerViewHolder(layoutView);
        return customerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.firstName.setText(customerList.get(position).getTitle());
        if(customerList.get(position).getTitle()!=null){
            holder.firstName.setText(customerList.get(position).getTitle());
        }
        if(customerList.get(position).getManufacturer()!=null){
            holder.lastName.setText(customerList.get(position).getManufacturer());
        }
        if(customerList.get(position).getPrice()!=null){
            holder.email.setText(customerList.get(position).getPrice());
        }
        if(customerList.get(position).getTitle()!= null){
            holder.customerId.setText(customerList.get(position).getQuantity());
        }
    }

    @Override
    public int getItemCount() {
        return this.customerList.size();
    }
}
