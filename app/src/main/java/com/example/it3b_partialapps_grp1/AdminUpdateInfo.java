package com.example.it3b_partialapps_grp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminUpdateInfo extends AppCompatActivity {

    Button btnUpdate, btnClear;
    EditText tName,tContactNo,tUsername;

    DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_info);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnClear = findViewById(R.id.btnClearTextAdmin);
        tName = findViewById(R.id.editTextname);
        tContactNo = findViewById(R.id.editTextContactNo);
        tUsername = findViewById(R.id.editTextUsername);
        //tPassword = findViewById(R.id.editTextTextPassword);

        Bundle bundle = getIntent().getExtras();
        String ids = bundle.getString("adminkey");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersRef = FirebaseDatabase.getInstance().getReference().child("admin").child(ids);
                Map<String, Object> updates = new HashMap<>();
                updates.put("contact", tContactNo.getText().toString());
                updates.put("fullname", tName.getText().toString());
                updates.put("gender", tUsername.getText().toString());

                usersRef.updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data updated successfully
                                tName.setText("");
                                tContactNo.setText("");
                                tUsername.setText("");

                                Toast.makeText(AdminUpdateInfo.this, "Name updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while updating data
                                Toast.makeText(AdminUpdateInfo.this, "Failed to update name: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}