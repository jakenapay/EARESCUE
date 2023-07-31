package com.example.it3b_partialapps_grp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateContactActivity extends AppCompatActivity {

    TextView conId;
    EditText location,cNumber,faciName,faciAdd;

    Button bttnUp , bttnClear;

    DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        String toContact = bundle.getString("typeofContact");
        String cid = bundle.getString("contactID");
        String fname = bundle.getString("facilityName");
        String fadd = bundle.getString("facilityAddress");
        String loc = bundle.getString("Location");
        String cNum = bundle.getString("contactNo");
        String datePosted = bundle.getString("currDate");

        conId = findViewById(R.id.textcontactId);
        location = findViewById(R.id.editCurrentLocation);
        cNumber = findViewById(R.id.editCurrentNumber);
        faciName = findViewById(R.id.editCurrentName);
        faciAdd = findViewById(R.id.editCurrentAddress);

        bttnUp = findViewById(R.id.bttnUpdateContact);
        bttnClear = findViewById(R.id.btnClearTextAdmin);



        conId.setText(String.valueOf(user));
        location.setText(loc);
        cNumber.setText(cNum);
        faciName.setText(fname);
        faciAdd.setText(fadd);




        bttnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                usersRef = FirebaseDatabase.getInstance().getReference().child(toContact).child(cid);
                Map<String, Object> updates = new HashMap<>();
                updates.put("contact", cNumber.getText().toString());
                updates.put("datenow", datePosted);
                updates.put("location", location.getText().toString());
                updates.put("nAddress", faciAdd.getText().toString());
                updates.put("nofFacility", faciName.getText().toString());
                updates.put("typeOfContact", toContact);
                updates.put("uEmail", user.getEmail());
                updates.put("uId", user.getUid());
                updates.put("Updated" , formattedDate);

               usersRef.updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data updated successfully
                                Intent i = new Intent(UpdateContactActivity.this ,ContactList.class);
                                startActivity(i);
                                Toast.makeText(UpdateContactActivity.this, "Name updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while updating data
                                Toast.makeText(UpdateContactActivity.this, "Failed to update name: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });






    }
}