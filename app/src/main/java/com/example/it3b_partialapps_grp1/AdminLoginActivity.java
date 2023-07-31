package com.example.it3b_partialapps_grp1;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class



public class AdminLoginActivity extends AppCompatActivity {
    Button toAdminForm;
    EditText username, pword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mAuth = FirebaseAuth.getInstance();

        toAdminForm = findViewById(R.id.btnLogin);
        username = findViewById(R.id.editTextUsername);
        pword = findViewById(R.id.editTextPassword);

        toAdminForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(username.getText());
                password = String.valueOf(pword.getText());

                if (!isValidEmailFormat(email)) {
                    Toast.makeText(AdminLoginActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(AdminLoginActivity.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();


                                    Bundle bundle = new Bundle();
                                    Intent i = new Intent(AdminLoginActivity.this, MainAdminActivity.class);
                                    bundle.putString("adminkey", user.getUid());
                                    bundle.putString("name", user.getEmail() );
                                    i.putExtras(bundle);
                                    startActivity(i);

                                    mDatabase = FirebaseDatabase.getInstance().getReference();
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
                                    activityLog act = new activityLog(String.valueOf(formattedDate),
                                            "", "online", user.getUid(), user.getEmail());
                                    mDatabase.child("activityLog").child(String.valueOf(user.getUid())).setValue(act);
                                    Toast.makeText(AdminLoginActivity.this, "Log in", Toast.LENGTH_LONG).show();

                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(AdminLoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //(null);
                                }
                            }
                        });





            }
        });
    }

    private boolean isValidEmailFormat(String email) {
        // Define the regular expression pattern for allowed email formats
        String pattern = "^(?:[a-zA-Z0-9._%+-]+@(?:gmail|yahoo|ymail|outlook|hotmail)\\.(?:com))$";

        // Compile the pattern into a regex object
        Pattern regex = Pattern.compile(pattern);

        // Match the email against the regex pattern
        Matcher matcher = regex.matcher(email);

        // Return true if the email matches the pattern, false otherwise
        return matcher.matches();
    }
}

                /* FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference adminsRef = database.getReference("admin");
                String enteredUsername = username.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();

                // Query the database to find the admin with the entered username
                Query query = adminsRef.orderByChild("username").equalTo(enteredUsername);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot adminSnapshot : dataSnapshot.getChildren()) {
                                String storedPassword = adminSnapshot.child("password").getValue(String.class);
                                String adminKey = adminSnapshot.getKey();
                                String uname = adminSnapshot.child("fullname").getValue(String.class);
                                // Compare the entered password with the stored password
                                if (storedPassword.equals(enteredPassword)) {
                                    // Login successful

                                    Toast.makeText(AdminLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                    // Perform any necessary actions or navigate to the next screen

                                    Bundle bundle = new Bundle();
                                    Intent i = new Intent(AdminLoginActivity.this, MainAdminActivity.class);
                                    bundle.putString("adminkey", adminKey);
                                    bundle.putString("name", uname );
                                    i.putExtras(bundle);
                                    startActivity(i);
                                } else {
                                    // Incorrect password
                                    Toast.makeText(AdminLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // Admin not found
                            Toast.makeText(AdminLoginActivity.this, "Admin not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Database read error
                        Toast.makeText(AdminLoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/