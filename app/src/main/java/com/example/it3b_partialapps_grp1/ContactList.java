package com.example.it3b_partialapps_grp1;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactList extends AppCompatActivity {

    TextView test;
    ListView contactData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("conLabel");
        setContentView(R.layout.activity_contact_list);
        test = findViewById(R.id.textLabel);


        test.setText("Update " + "Contact " + stuff);



        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(stuff);
        ArrayList<String> ambulanceList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ambulanceList);
        contactData = findViewById(R.id.contactLabel_list);
        contactData.setAdapter(adapter);



        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ambulanceList.clear(); // Clear the existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    String ambulanceInfo = ""; // String to store the concatenated information


                    String location = snapshot.child("location").getValue(String.class);
                    String contactNumber = snapshot.child("contact").getValue(String.class);
                    String dateofpost = snapshot.child("datenow").getValue(String.class);
                    String facilityName = snapshot.child("nofFacility").getValue(String.class);
                    String facilityAddress = snapshot.child("nAddress").getValue(String.class);

                    ambulanceInfo += key + "\n";
                    ambulanceInfo += facilityName + "\n";
                    ambulanceInfo += facilityAddress + "\n";
                    ambulanceInfo += location + "\n";
                    ambulanceInfo += contactNumber + "\n";
                    ambulanceInfo += dateofpost + "\n";
                    ambulanceList.add("\n" + ambulanceInfo);
                }
                adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FetchData", "Failed to fetch data: " + databaseError.getMessage());
            }

        });
        contactData.setClickable(true);
        contactData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String o = (String) contactData.getItemAtPosition(position);
                String[] splitData = o.split("\n");

                if (splitData.length >= 1) {
                    String cId = splitData[1].trim();
                    String fname = splitData[2].trim();
                    String fadd = splitData[3].trim();
                    String loc = splitData[4].trim();
                    String contactNo = splitData[5].trim();
                    String currentDate = splitData[6].trim();


                    Log.d(TAG, "Selected: " + cId);
                    Log.d(TAG, "Selected: " + fname);
                    Log.d(TAG, "Selected: " + fadd);
                    Log.d(TAG, "Selected: " + loc);
                    Log.d(TAG, "Selected: " + contactNo);

                    Intent i = new Intent(ContactList.this, UpdateContactActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("typeofContact" , stuff);
                    bundle.putString("contactID", cId);
                    bundle.putString("facilityName", fname);
                    bundle.putString("facilityAddress", fadd);
                    bundle.putString("Location", loc);
                    bundle.putString("contactNo", contactNo);
                    bundle.putString("currDate", currentDate);
                    i.putExtras(bundle);
                    startActivity(i);
                } else {
                    // Log the splitData array to debug the data format
                    Log.e(TAG, "Invalid data format: " + Arrays.toString(splitData));
                    Toast.makeText(getApplicationContext(), "Invalid data format", Toast.LENGTH_SHORT).show();
                }//dito



            }
        });
    }
}