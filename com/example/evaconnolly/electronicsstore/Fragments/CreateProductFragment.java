package com.example.evaconnolly.electronicsstore.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.evaconnolly.electronicsstore.Objects.Product;
import com.example.evaconnolly.electronicsstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import static android.app.Activity.RESULT_OK;

public class CreateProductFragment extends Fragment {

    private Button saveButton;
    private ImageButton imageButton;
    private EditText Title, Manufacturer, Price, Quantity;
    private StorageReference ProductReference;
    private String title;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseProduct;
    private String productTitle ,productManufacturer, productPrice, productQuantity, categorySpinner;
    private String downloadUrl, currentUser;
    private FirebaseAuth mAuth;
    private Spinner mySpinner;

    private static final int image_pick = 1;
    private Uri ImageUri;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_product, container, false);
        saveButton = (Button) view.findViewById(R.id.saveBtn);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        Title = (EditText) view.findViewById(R.id.title);
        Manufacturer = (EditText) view.findViewById(R.id.manufacturer);
        Price = (EditText) view.findViewById(R.id.price);
        Quantity = (EditText) view.findViewById(R.id.quantity);
        mySpinner = (Spinner) view.findViewById(R.id.categorySpinner);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseProduct = mFirebaseDatabase.getReference();

        Spinner dropdown = mySpinner;
        String[] categories = new String[]{"Headphones", "Earphones", "Activity Watch", "Speakers"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        dropdown.setAdapter(adapter);

        ProductReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        currentUser= mAuth.getCurrentUser().getUid();

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectProductImage();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateInfo();
            }
        });

        return view;
    }

    private void ValidateInfo() {

        productTitle = Title.getText().toString();
        productManufacturer = Manufacturer.getText().toString();
        productQuantity = Quantity.getText().toString();
        productPrice = Price.getText().toString();
        categorySpinner = mySpinner.getSelectedItem().toString();

        if(ImageUri == null){
            Toast.makeText(getActivity(), "You need to include a picture of the product..", Toast.LENGTH_SHORT).show();
        }
        else if(productTitle == null){
            Toast.makeText(getActivity(), "You need to include a title for the product..", Toast.LENGTH_SHORT).show();
        }
        else if(productManufacturer == null){
            Toast.makeText(getActivity(), "You need to include a manufacturer for the product..", Toast.LENGTH_SHORT).show();
        }
        else if(productPrice== null ){
            Toast.makeText(getActivity(), "You need to include a price for the product..", Toast.LENGTH_SHORT).show();
        }
        else if(productQuantity == null){
            Toast.makeText(getActivity(), "You need to include a quantity for the product..", Toast.LENGTH_SHORT).show();
        }
        else{
            saveProduct();
        }

    }

    private void selectProductImage() {
        Intent imageIntent = new Intent();
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        imageIntent.setType("image/");
        startActivityForResult(imageIntent, image_pick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == image_pick && resultCode==RESULT_OK && data!=null){
            ImageUri = data.getData();
            imageButton.setImageURI(ImageUri);
        }
    }

    private void saveProduct() {
        title = Title.getText().toString().trim();

        StorageReference filePath = ProductReference.child("Product Images").child(ImageUri.getLastPathSegment() + title + "jpg");
        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task){
                if(task.isSuccessful()){

                    downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                    Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    saveProducts();
                }
                else{
                    String message = task.getException().getMessage();
                    Toast.makeText(getActivity(), "Error occurred: " + message, Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void saveProducts(){
        String title = Title.getText().toString().trim();
        String manufacturer = Manufacturer.getText().toString().trim();
        String price = Price.getText().toString().trim();
        String quantity = Quantity.getText().toString().trim();
        categorySpinner = mySpinner.getSelectedItem().toString();

        Product product = new Product(title, manufacturer, price, downloadUrl, quantity, categorySpinner);

        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();

        databaseProduct.child("Products").child(title).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void aVoid){
                Toast.makeText(getActivity(), "saved", Toast.LENGTH_LONG).show();
            }
        });

    }
}
