package com.projects.rentalease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText edtSignUpFullName;
    private TextInputEditText edtSignUpMobile;
    private MaterialButton btnSignUp;
    private TextView txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtSignUpFullName = findViewById(R.id.edtSignUpFullName);
        edtSignUpMobile = findViewById(R.id.edtSignUpMobile);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignIn = findViewById(R.id.txtSignIn);

        // Set up click listener for sign-up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign-up button click event
                String fullName = edtSignUpFullName.getText().toString();
                String mobile = edtSignUpMobile.getText().toString();

                // Validate input
                if (fullName.isEmpty() || mobile.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter your full name and mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO: Save user data to Firebase

                // Start sign-in activity
                Intent signInIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(signInIntent);
            }
        });

        // Set up click listener for sign-in text
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign-in text click event
                Intent signInIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(signInIntent);
            }
        });
    }
}














}
