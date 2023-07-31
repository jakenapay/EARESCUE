package com.example.it3b_partialapps_grp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MainAdminActivity extends AppCompatActivity {
    Button toAddContact, toAddadmin, toDeleteContact, toUpdateContact, toUpdateInfo, toProfile;
    TextView admin;

    private FirebaseAuth mAuth;

    DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        toAddContact = findViewById(R.id.buttonAddContact);
        toAddadmin = findViewById(R.id.buttonAddAdmin);
        toDeleteContact = findViewById(R.id.buttonDelContact);
        toUpdateContact = findViewById(R.id.buttonUpContact);
        toUpdateInfo = findViewById(R.id.buttonUpInfo);
        toProfile = findViewById(R.id.btnProfile);
        admin = findViewById(R.id.txtTest);
        Bundle bundle = getIntent().getExtras();
        String adminId = bundle.getString("adminkey");
        String uname = bundle.getString("name");


        admin.setText(uname);

        toAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent( MainAdminActivity.this, AdminAddContact.class);
                Bundle bundle = new Bundle();
                Intent i = new Intent(MainAdminActivity.this, AdminAddContact.class);
                bundle.putString("aId", adminId);
                bundle.putString("aEmail", uname);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        toAddadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainAdminActivity.this, RegisterNewAdmin.class);
                startActivity(intent);
            }
        });

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainAdminActivity.this, AdminProfile.class);
                startActivity(intent);
            }
        });

        toDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainAdminActivity.this, AdminDeleteContact.class);
                startActivity(intent);
            }
        });

        toUpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainAdminActivity.this, AdminUpdateContact.class);
                startActivity(intent);
            }
        });

        toUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainAdminActivity.this, AdminUpdateInfo.class);
                Bundle bundle = new Bundle();

                bundle.putString("adminkey", adminId);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        try {
            BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
            bottomNavigationView.setSelectedItemId(R.id.nav_adminhome);

            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_adminhome) {
                    startActivity(new Intent(getApplicationContext(), MainAdminActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
                } else if (itemId == R.id.nav_adminactivitylogs) {
                    startActivity(new Intent(getApplicationContext(), AdminActivityLogs.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }  else if (itemId == R.id.nav_adminprofile) {
                    startActivity(new Intent(getApplicationContext(), AdminProfile.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
                }  else if (itemId == R.id.nav_adminlogout) {
                    LocalDateTime myDateObj = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        myDateObj = LocalDateTime.now();
                    }
                    System.out.println("Before formatting: " + myDateObj);
                    DateTimeFormatter myFormatObj = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    }

                    String formattedDate = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        formattedDate = myDateObj.format(myFormatObj);
                    }
                    System.out.println("After formatting: " + formattedDate);


                    usersRef = FirebaseDatabase.getInstance().getReference().child("activityLog").child(adminId);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("outtime", formattedDate);
                    updates.put("status", "offline");

                    usersRef.updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Data updated successfully
                                    FirebaseAuth.getInstance().signOut();
                                    Toast.makeText(MainAdminActivity.this, "Log out Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error occurred while updating data
                                    Toast.makeText(MainAdminActivity.this, "Failed to Log out: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
                } else {
                    return false;
                }
            });
        }catch (Exception e){


            // Display a Toast message with the error
            Toast.makeText(getApplicationContext(), "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}