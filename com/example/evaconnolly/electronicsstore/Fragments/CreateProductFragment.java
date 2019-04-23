package com.example.evaconnolly.electronicsstore.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.evaconnolly.electronicsstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class CreateProductFragment extends Fragment {

    private Button saveButton;
    private ImageButton imageButton;
    private EditText Title, Manufacturer, Price, Quantity;
    private StorageReference ProductReference;
    private String current_user_id;
    private DatabaseReference ProductRef, SaveProductRef;
    private String productTitle ,productManufacturer, productPrice, productQuantity;
    private String saveThisDate, saveThisTime, productDetails, downloadUrl, currentUser;
    private FirebaseAuth mAuth;

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


        ProductReference = FirebaseStorage.getInstance().getReference();

        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
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

        if(ImageUri == null){
            Toast.makeText(getActivity(), "You need to include a picture of the product..", Toast.LENGTH_SHORT).show();
        }
        else if(productTitle == null){
            Toast.makeText(getActivity(), "You need to include a title for the product..", Toast.LENGTH_SHORT).show();
        }
        else if(productManufacturer == null){
            Toast.makeText(getActivity(), "You need to include a manufacturer for the product..", Toast.LENGTH_SHORT).show();
        }
        else if(productPrice == null){
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
        Calendar mDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveThisDate = currentDate.format(mDate.getTime()); //get current date and save in saveThisDate

        Calendar mTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveThisTime = currentTime.format(mTime.getTime());

        productDetails = saveThisDate + saveThisTime;

        StorageReference filePath = ProductReference.child("Product Images").child(ImageUri.getLastPathSegment() + productDetails + "jpg");
        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task){
                if(task.isSuccessful()){

                    downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                    Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    SavePostInfo();
                }
                else{
                    String message = task.getException().getMessage();
                    Toast.makeText(getActivity(), "Error occured: " + message, Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void SavePostInfo() {

        HashMap productMap = new HashMap();
        productMap.put("Title", productTitle);
        productMap.put("Manufacturer", productManufacturer);
        productMap.put("Price", productPrice);
        productMap.put("Quantity", productQuantity);
        productMap.put("Product Image", downloadUrl);
        ProductRef.child(productDetails).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    BrowseProductFragment browseProductFragment = new BrowseProductFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.mainContainer, browseProductFragment).commit();
                    Toast.makeText(getActivity(), "New product has been updated successfully tnx", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getActivity(), "Error occured sry", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
