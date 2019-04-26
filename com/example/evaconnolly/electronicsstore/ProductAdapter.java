package com.example.evaconnolly.electronicsstore;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.evaconnolly.electronicsstore.R;
import com.example.evaconnolly.electronicsstore.Objects.Product;

import java.util.List;
public class ProductAdapter extends ArrayAdapter<Product>{

    private Activity activity;
    private List<Product> productList;
    private LayoutInflater inflater;


    public ProductAdapter(Activity activity, List<Product> productList){
        super(activity, R.layout.all_products_layout, productList);
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(inflater== null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView = inflater.inflate(R.layout.all_products_layout,null);

        TextView textViewTitle = convertView.findViewById(R.id.title);
        TextView textViewManufacturer= convertView.findViewById(R.id.manufacturer);
        TextView textViewPrice = convertView.findViewById(R.id.price);
        TextView textViewQuantity = convertView.findViewById(R.id.quantity);
        Product product = productList.get(position);
        //ImageView imageView = convertView.findViewById(R.id.bookImageView);


        textViewTitle.setText(product.getTitle());
        textViewManufacturer.setText(product.getManufacturer());
        textViewPrice.setText(product.getPrice());
        textViewQuantity.setText(product.getQuantity());
       // textViewCategory.setText(product.getCategory());

        return convertView;
    }
}
