package com.example.it3b_partialapps_grp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminAddContact extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    Button btnSave, btnClear;

    EditText editContact, editLoc, editTypeCon, editFacilityName, editFacilityAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_contact);
        mAuth = FirebaseAuth.getInstance();


        btnSave = findViewById(R.id.bttnSave);
        btnClear = findViewById(R.id.bttnClear);

        editContact = findViewById(R.id.editContact);
        editLoc = findViewById(R.id.editLocation);
        editTypeCon = findViewById(R.id.editTypeofContact);
        editFacilityName = findViewById(R.id.editfacilityName);
        editFacilityAddress = findViewById(R.id.editfacilityAddress);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        try {
            BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
            //bottomNavigationView.setSelectedItemId(R.id.nav_home);

            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
                } else if (itemId == R.id.nav_login) {
                    startActivity(new Intent(getApplicationContext(), AdminLoginActivity.class));
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
    public void sendData(View view){
        writeNewUser();
        clearUser();
    }

    public void writeNewUser(){
        Bundle bundle = getIntent().getExtras();
        String aId = bundle.getString("aId");
        String aEmail = bundle.getString("aEmail");

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

        addContact contact = new addContact(editContact.getText().toString(),
             editLoc.getText().toString(),
             editTypeCon.getText().toString(), aId,aEmail,formattedDate, editFacilityName.getText().toString() , editFacilityAddress.getText().toString());
        mDatabase.child(contact.getTypeOfContact().toString()).push().setValue(contact);
        Toast.makeText(AdminAddContact.this, "Successfully Added data", Toast.LENGTH_LONG).show();
    }

    public void clearUser(){
        editContact.setText("");
        editLoc.setText("");
        editTypeCon.setText("");
        editFacilityName.setText("");
        editFacilityAddress.setText("");
    }


}