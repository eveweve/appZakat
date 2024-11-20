package com.example.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zakatapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button zakatButton, historyButton, logoutBUtton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        zakatButton = findViewById(R.id.zakatButton);
        historyButton = findViewById(R.id.historyButton);
        logoutBUtton = findViewById(R.id.loginButton);

        zakatButton.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, ZakatActivity.class));
        });

        historyButton.setOnClickListener( v -> {
            startActivity(new Intent(DashboardActivity.this, HistroyActivity.class));
        });

        logoutBUtton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}
