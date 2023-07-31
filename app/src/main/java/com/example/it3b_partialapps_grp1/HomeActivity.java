package com.example.it3b_partialapps_grp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    Button ambulance,firefighter,police,financial,road,healthcare,disaster,violence;
    String sampleContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ambulance = findViewById(R.id.button1);
        firefighter = findViewById(R.id.button2);
        police = findViewById(R.id.button3);
        financial = findViewById(R.id.button4);
        road = findViewById(R.id.button5);
        healthcare = findViewById(R.id.button6);
        disaster = findViewById(R.id.button7);
        violence = findViewById(R.id.button8);

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sampleContact = "ambulance";
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("contactLabel", sampleContact);
                i.putExtras(bundle);
                startActivity(i);

            }
        });

        firefighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                sampleContact = "firefighter";
                Bundle bundle = new Bundle();

                bundle.putString("contactLabel", sampleContact);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                sampleContact = "police";
                Bundle bundle = new Bundle();

                bundle.putString("contactLabel", sampleContact);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                sampleContact = "finance";
                Bundle bundle = new Bundle();

                bundle.putString("contactLabel", sampleContact);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                sampleContact = "roadside";
                Bundle bundle = new Bundle();

                bundle.putString("contactLabel", sampleContact);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        healthcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                sampleContact = "health";
                Bundle bundle = new Bundle();

                bundle.putString("contactLabel", sampleContact);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                sampleContact = "disaster";
                Bundle bundle = new Bundle();
                bundle.putString("contactLabel", sampleContact);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        violence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                sampleContact = "domestic";
                Bundle bundle = new Bundle();

                bundle.putString("contactLabel", sampleContact);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

      try {
          BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
          bottomNavigationView.setSelectedItemId(R.id.nav_home);

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
}