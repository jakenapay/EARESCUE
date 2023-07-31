package com.example.it3b_partialapps_grp1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
//    TextView getContactLabel, getLocationLabel,getConLabel;
//    ListView listView;

    EditText textSearch;
    Button  search, textclear;

    // Helper method to capitalize the first letter of a string
    private String capitalizeText(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        search = findViewById(R.id.bttnSearch);
        textSearch = findViewById(R.id.editTextSearch);
        textclear = findViewById(R.id.bttnClearTextField);

        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("contactLabel");

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(stuff);
        ArrayList<String> ambulanceList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ambulanceList);
        ListView listView = findViewById(R.id.my_list);
        listView.setAdapter(adapter);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ambulanceList.clear(); // Clear the existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ambulanceInfo = "";
                    if (snapshot.exists()) {
                        String location = snapshot.child("location").getValue(String.class);
                        String contactNumber = snapshot.child("contact").getValue(String.class);
                        String dateofpost = snapshot.child("datenow").getValue(String.class);
                        String facilityName = snapshot.child("nofFacility").getValue(String.class);
                        String facilityAddress = snapshot.child("nAddress").getValue(String.class);
                        String dateofUpdate = snapshot.child("Updated").getValue(String.class);

                        if (dateofUpdate == null) {
                            dateofUpdate = "";
                        }

                        ambulanceInfo += facilityName + "\n";
                        ambulanceInfo += facilityAddress + "\n";
                        ambulanceInfo += location + "\n";
                        ambulanceInfo += contactNumber + "\n";
                        ambulanceInfo += "Posted: " + dateofpost + "\n";
                        ambulanceInfo += "Updated: " + dateofUpdate + "\n";
                        ambulanceList.add("\n" + ambulanceInfo);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ContactActivity.this, "Error fetching data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Firebase", "Error fetching data: " + databaseError.getMessage(), databaseError.toException());
            }
        });

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String o = (String) listView.getItemAtPosition(i);
                String[] splitData = o.split("\n");

                if (splitData.length >= 1) {
                    String phoneNumber = splitData[4].trim();

                    Log.d(TAG, "Selected: " + phoneNumber);

                    if (phoneNumber.matches("\\d+")) {
                        // Open the phone dialer with the selected phone number
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        startActivity(intent);
                    } else {
                        // Handle other types of selections or display a message
                        Toast.makeText(getApplicationContext(), "Invalid phone number format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Log the splitData array to debug the data format
                    Log.e(TAG, "Invalid data format: " + Arrays.toString(splitData));
                    Toast.makeText(getApplicationContext(), "Invalid data format", Toast.LENGTH_SHORT).show();
                }//dito


            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = textSearch.getText().toString().trim().toLowerCase(); // Get the lowercase search text and remove leading/trailing spaces
                DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(stuff);
                ArrayList<String> ambulanceList = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ContactActivity.this, android.R.layout.simple_list_item_1, ambulanceList);
                ListView listView = findViewById(R.id.my_list);
                listView.setAdapter(adapter);

                dataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ambulanceList.clear(); // Clear the existing data
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String location = snapshot.child("location").getValue(String.class);
                            String facilityName = snapshot.child("nofFacility").getValue(String.class);

                            // Convert location and facilityName to capitalized form
                            location = capitalizeText(location);
                            facilityName = capitalizeText(facilityName);

                            // Perform a case-insensitive search with lowercase search text
                            if (location.toLowerCase().contains(searchText) || facilityName.toLowerCase().contains(searchText)) {
                                String ambulanceInfo = "";
                                String contactNumber = snapshot.child("contact").getValue(String.class);
                                String dateofpost = snapshot.child("datenow").getValue(String.class);
                                String facilityAddress = snapshot.child("nAddress").getValue(String.class);
                                String dateofUpdate = snapshot.child("Updated").getValue(String.class);

                                if (dateofUpdate == null) {
                                    dateofUpdate = "";
                                }

                                ambulanceInfo += facilityName + "\n";
                                ambulanceInfo += facilityAddress + "\n";
                                ambulanceInfo += location + "\n";
                                ambulanceInfo += contactNumber + "\n";
                                ambulanceInfo += "Posted: " + dateofpost + "\n";
                                ambulanceInfo += "Updated: " + dateofUpdate + "\n";
                                ambulanceList.add("\n" + ambulanceInfo);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ContactActivity.this, "Error fetching data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error fetching data: " + databaseError.getMessage(), databaseError.toException());
                    }
                });
            }
        });

        textclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                textSearch.setText("");

                DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(stuff);
                ArrayList<String> ambulanceList = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ContactActivity.this, android.R.layout.simple_list_item_1, ambulanceList);
                ListView listView = findViewById(R.id.my_list);
                listView.setAdapter(adapter);

                dataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ambulanceList.clear(); // Clear the existing data
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String ambulanceInfo = "";
                            if (snapshot.exists()) {
                                String location = snapshot.child("location").getValue(String.class);
                                String contactNumber = snapshot.child("contact").getValue(String.class);
                                String dateofpost = snapshot.child("datenow").getValue(String.class);
                                String facilityName = snapshot.child("nofFacility").getValue(String.class);
                                String facilityAddress = snapshot.child("nAddress").getValue(String.class);
                                String dateofUpdate = snapshot.child("Updated").getValue(String.class);

                                if (dateofUpdate == null) {
                                    dateofUpdate = "";
                                }

                                ambulanceInfo += facilityName + "\n";
                                ambulanceInfo += facilityAddress + "\n";
                                ambulanceInfo += location + "\n";
                                ambulanceInfo += contactNumber + "\n";
                                ambulanceInfo += "Posted: " + dateofpost + "\n";
                                ambulanceInfo += "Updated: " + dateofUpdate + "\n";
                                ambulanceList.add("\n" + ambulanceInfo);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ContactActivity.this, "Error fetching data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error fetching data: " + databaseError.getMessage(), databaseError.toException());
                    }
                });
            }
        });


    }
}