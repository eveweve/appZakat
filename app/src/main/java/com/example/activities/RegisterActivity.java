package com.example.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.models.User;
import com.example.zakatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText nameField, emailField, passwordField;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        nameField = findViewById(R.id.nameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser(){
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

                        User newUser = new User(user.getUid(), name, email);
                        userRef.child(user.getUid()).setValue(newUser);

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(this, "Regsiter is failde" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
