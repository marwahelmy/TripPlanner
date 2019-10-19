package com.waleed.tripplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.waleed.tripplanner.R;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "userFireBase";
    private static int SPLASH_SCREEN_TIME_OUT = 3000;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if (firebaseUser != null) {
//                    Log.d(TAG, "name: " + firebaseUser.getDisplayName());
//                    Log.d(TAG, "email: " + firebaseUser.getEmail());
//                    Log.d(TAG, "photoUrl: " + firebaseUser.getPhotoUrl());
                    i = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }

                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }

}
