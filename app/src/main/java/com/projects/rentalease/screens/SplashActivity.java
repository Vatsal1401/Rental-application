package com.projects.rentalease.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread t1 = new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    //  Intent i = new Intent(new Intent(SplashActivity.this, MainActivity.class));
                    //startActivity(i);
                    //  startActivity(new Intent(SplashActivity.this, MyMenuActivity.class));
                    startActivity(new Intent(SplashActivity.this, HomeFragment.class));
                }

            }
        };
        t1.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
