package com.example.it3b_partialapps_grp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfile extends AppCompatActivity {
    TextView uName , uContact, uEmail, uId, uGender;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        uName = findViewById(R.id.textuserName);
        uContact = findViewById(R.id.textuserContact);
        uEmail = findViewById(R.id.textuserEmail);
        uGender = findViewById(R.id.textuserGender);
        uId = findViewById(R.id.textuserID);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("admin");


        DatabaseReference adminRef = databaseReference.child(user.getUid());
        adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the admin exists in the database
                if (dataSnapshot.exists()) {
                    // Fetch the data fields
                    String contact = dataSnapshot.child("contact").getValue(String.class);
                    String fullname = dataSnapshot.child("fullname").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);

                    // Do something with the fetched data
                    uName.setText("Fullname/Name: "+fullname);
                    uContact.setText("Contact: " +contact);
                    uGender.setText("Gender:"+ gender);
                    uId.setText("user ID: " +user.getUid());
                    uEmail.setText("Email: " + user.getEmail());
                } else {
                    Log.d("Firebase", "Admin with ID " + user.getUid() + " does not exist.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.e("Firebase", "Failed to read value.", databaseError.toException());
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.nav_adminprofile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_adminhome) {
                startActivity(new Intent(getApplicationContext(), MainAdminActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_adminactivitylogs) {
                startActivity(new Intent(getApplicationContext(), AdminActivityLogs.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.nav_adminprofile) {
                startActivity(new Intent(getApplicationContext(), AdminProfile.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }  else if (itemId == R.id.nav_adminlogout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0,0);
                finish();
                return true;
            } else {
                return false;
            }
        });

    }
}