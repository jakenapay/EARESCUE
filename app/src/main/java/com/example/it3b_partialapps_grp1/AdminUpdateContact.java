package com.example.it3b_partialapps_grp1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUpdateContact extends AppCompatActivity {

    private ListView listView;
    private String[] texts = {
            "ambulance",
            "finance",
            "firefighter",
            "police",
            "roadside",
            "health",
            "disaster",
            "violence"
            // Add more texts as needed
    };

    DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_contact);



        listView = findViewById(R.id.category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, texts);
        listView.setAdapter(adapter);


        listView.setAdapter(adapter);  listView = findViewById(R.id.category_list);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = texts[position];
                Log.e(TAG, "data: " + selectedItem);
                Intent i = new Intent(AdminUpdateContact.this, ContactList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("conLabel", selectedItem);
                    i.putExtras(bundle);
                    startActivity(i);


            }
        });
    }

}