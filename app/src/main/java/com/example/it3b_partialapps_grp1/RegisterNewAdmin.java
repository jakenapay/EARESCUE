package com.example.it3b_partialapps_grp1;

import static android.content.ContentValues.TAG;

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
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterNewAdmin extends AppCompatActivity {
    private DatabaseReference mDatabase;
    Button clearText;
    EditText adminFname, adminContact, adminEmail, adminPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_admin);

        adminEmail = findViewById(R.id.editTextUsername);
        adminPassword = findViewById(R.id.editTextPassword);

    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }*/
    public void addAdmin(View view){
        String email, password;
        email = String.valueOf(adminEmail.getText());
        password = String.valueOf(adminPassword.getText());

        if (!isValidEmailFormat(email)) {
            Toast.makeText(RegisterNewAdmin.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterNewAdmin.this, "Register Success.",
                                    Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterNewAdmin.this, "Register failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
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


   /* public void writeNewAdmin(){
        User user = new User(adminFname.getText().toString(),
                adminContact.getText().toString(),
                adminUsername.getText().toString(),
                adminPassword.getText().toString()
                );
        mDatabase.child("admin").push().setValue(user);
        Toast.makeText(RegisterNewAdmin.this, "Successfully Added data", Toast.LENGTH_LONG).show();
    }

    public void clearUserAdmin(){
        adminFname.setText("");
        adminContact.setText("");
        adminUsername.setText("");
        adminPassword.setText("");
    }*/
}