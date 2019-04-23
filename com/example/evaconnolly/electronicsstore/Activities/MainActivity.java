package com.example.evaconnolly.electronicsstore.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.evaconnolly.electronicsstore.Fragments.BrowseProductFragment;
import com.example.evaconnolly.electronicsstore.Fragments.CreateProductFragment;
import com.example.evaconnolly.electronicsstore.Fragments.ReviewFragment;
import com.example.evaconnolly.electronicsstore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mainToolbar;
    private FirebaseAuth mAuth;
    DatabaseReference userDetails;

    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Home");

        drawerLayout = (DrawerLayout)  findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View navView = navigationView.inflateHeaderView(R.layout.nav_header);

        getAccountType();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new BrowseProductFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                MainActivity(item);
                return false;
            }
        });
    }

   /* private void BackToStart() {
        Intent homeIntent = new Intent(MainActivity.this, StartActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void MainActivity(MenuItem item) {
        switch(item.getItemId()){
            case R.id.navBrowseProducts:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new BrowseProductFragment()).commit();
                 break;

            case R.id.navLogout:
                mAuth.signOut();
                BackToStart();
                break;

            case R.id.navReview:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new ReviewFragment()).commit();
                break;

            case R.id.navCreateProduct:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new CreateProductFragment()).commit();
        }

    }

    private void BackToStart() {
    }

    private void getAccountType(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        }
        userDetails = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        userDetails.keepSynced(true);

        userDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String accountType = dataSnapshot.child("accountType").getValue().toString();

                if(accountType.equals("Customer"))
                {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.navigation);

                } else if (accountType.equals("Admin"))
                {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.admin_navigation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.mainToolbar, fragment)
                    .commit();
    }

    public void setFragmentBackstack(FragmentManager fragmentManager, Fragment fragment) {
        if (fragmentManager != null)
            fragmentManager.beginTransaction()
                    .replace(R.id.mainToolbar, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
    }

}