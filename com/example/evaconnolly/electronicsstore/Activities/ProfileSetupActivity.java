package com.example.evaconnolly.electronicsstore.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evaconnolly.electronicsstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileSetupActivity extends AppCompatActivity {

    private EditText Name, BuildingNumber, StreetName, City, Eircode;
    private  Button save;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id);

        City = (EditText) findViewById(R.id.city);
        Eircode = (EditText) findViewById(R.id.eircode);
        StreetName = (EditText) findViewById(R.id.streetName);
        BuildingNumber = (EditText) findViewById(R.id.buildingNumber);
        Name = (EditText) findViewById(R.id.name);
        save = (Button) findViewById(R.id.saveBtn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveAccountDetails();
            }
        });

    }

    private void saveAccountDetails() {

        String streetName = StreetName.getText().toString();
        String name = Name.getText().toString();
        String city = City.getText().toString();
        String buildingNumber = BuildingNumber.getText().toString();
        String eircode = Eircode.getText().toString();
        String accountType = "Customer";

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please enter a name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(streetName))
        {
            Toast.makeText(this, "Please enter your street name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(buildingNumber))
        {
            Toast.makeText(this, "Please enter your building number..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(city))
        {
            Toast.makeText(this, "Please enter your city..", Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap userMap = new HashMap();
            userMap.put("name", name);
            userMap.put("buildingNumber", buildingNumber);
            userMap.put("streetName", streetName);
            userMap.put("city", city);
            userMap.put("eircode", eircode);
            userMap.put("accountType", accountType);
            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ProfileSetupActivity.this, "accounted created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileSetupActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        String message = task.getException().getMessage();
                        Toast.makeText(ProfileSetupActivity.this, "There was an error creating your account" +message , Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
