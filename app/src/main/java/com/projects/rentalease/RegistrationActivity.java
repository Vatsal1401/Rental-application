package com.projects.rentalease;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.projects.rentalease.daos.UserDao;
import com.projects.rentalease.model.User;

public class RegistrationActivity extends AppCompatActivity {

    final int SIGN_IN_CODE = 1;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(
                this,googleSignInOptions
        );



        SignInButton signInButton = findViewById(R.id.sign_in_button);


        signInButton.setOnClickListener(
                view -> {
                    Intent intent = googleSignInClient.getSignInIntent();
                    startActivityForResult(intent,SIGN_IN_CODE);
                }
        );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_CODE){

            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.
                        getCredential(googleSignInAccount.getIdToken(),null);

                mAuth.signInWithCredential(authCredential)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                updateUI();
                            }else{
                                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } catch (ApiException e) {
                throw new RuntimeException(e);
                // check karo
            }
        }

    }

    private void updateUI() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            new UserDao().addUser(new User(user.getDisplayName(),user.getEmail(),user.getPhotoUrl().toString()));
            Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }
}

