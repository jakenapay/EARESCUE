package com.example.it3b_partialapps_grp1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivityLogs extends AppCompatActivity {

    private ListView listViewAdminActivityLogs;
    private List<String> activityLog;

    ListView logsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_activity_logs);

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("activityLog");
        ArrayList<String> logsList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, logsList);
        logsData = findViewById(R.id.listViewAdminActivityLogs);
        logsData.setAdapter(adapter);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                logsList.clear(); // Clear the existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    String logInfo = ""; // String to store the concatenated information


                    String logtime = snapshot.child("logtime").getValue(String.class);
                    String outtime = snapshot.child("outtime").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    String uid = snapshot.child("uid").getValue(String.class);
                    String username = snapshot.child("username").getValue(String.class);

                    logInfo += "id: " + key + "\n";
                    logInfo += "in time: " + logtime + "\n";
                    logInfo += "out time: " +outtime + "\n";
                    logInfo += "status" + status + "\n";
                    logInfo += "user email: " + username + "\n";
                    logsList.add("\n" + logInfo);
                }
                adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FetchData", "Failed to fetch data: " + databaseError.getMessage());
            }

        });

       /* FirebaseApp.initializeApp(this);
        listViewAdminActivityLogs = findViewById(R.id.listViewAdminActivityLogs);
        activityLog = new ArrayList<>();

        DatabaseReference logsRef = FirebaseDatabase.getInstance().getReference("activityLog");

        logsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activityLog.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    activityLog log = snapshot.getValue(activityLog.class);
                    if (log != null) {
                        String logMessage = "logtime: " + log.getLogtime()  + "\nouttime: " + log.getOuttime() + "\nstatus: " + log.getStatus() + "\nuid: " + log.getUid() + "\nusername: " + log.getUsername();
                        activityLog.add(logMessage);
                    }
                }

                // Display the logs in the ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminActivityLogs.this, android.R.layout.simple_list_item_1, activityLog);
                listViewAdminActivityLogs.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error, if any
            }*//*
        });*/

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.nav_adminactivitylogs);

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
