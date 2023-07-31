package com.example.it3b_partialapps_grp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminDeleteContact extends AppCompatActivity {
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference();

    Button delete;
    EditText deleteContact , typeofContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_contact);

        delete = findViewById(R.id.bttnDelete);
        deleteContact = findViewById(R.id.editDeletetContact);
        typeofContact = findViewById(R.id.typeofcontact);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference(typeofContact.getText().toString());
                Query query = databaseReference.orderByChild("contact").equalTo(deleteContact.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Delete the data using the removeValue() method
                            snapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Firebase", "Data deleted successfully.");
                                        Toast.makeText(AdminDeleteContact.this, "Data deleted successfull", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(AdminDeleteContact.this, "Error deleting data", Toast.LENGTH_SHORT).show();
                                        Log.e("Firebase", "Error deleting data:", task.getException());
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AdminDeleteContact.this, "Error querying data:", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error querying data:", databaseError.toException());
                    }
                });
                /*DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("ambulance").child("contact");
                String delContact = deleteContact.getText().toString();
                usersRef.child(delContact).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data deleted successfully
                                Toast.makeText(AdminDeleteContact.this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while deleting data
                                Toast.makeText(AdminDeleteContact.this, "Failed to delete contact: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });*/
            }

        });

    }
}