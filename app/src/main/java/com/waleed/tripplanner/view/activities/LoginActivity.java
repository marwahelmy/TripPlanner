package com.waleed.tripplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.waleed.tripplanner.R;
import com.waleed.tripplanner.viewmodel.SignViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView registerTextView;
    Button loginButton;
    SignViewModel signViewModel;
    ProgressBar progressBar;
    TextInputLayout textInputLayout_email, textInputLayout_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signViewModel = ViewModelProviders.of(this).get(SignViewModel.class);

        init();
    }

    private void init() {
        registerTextView = findViewById(R.id.registerTextView);
        loginButton = findViewById(R.id.loginButton);
        registerTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        textInputLayout_email = findViewById(R.id.textInputLayout_email);
        textInputLayout_password = findViewById(R.id.textInputLayout_password);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerTextView:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.loginButton:

                progressBar.setVisibility(View.VISIBLE);
                signViewModel.login(textInputLayout_email.getEditText().getText().toString(),
                        textInputLayout_password.getEditText().getText().toString());

                // to show the response for login operation
                showMessage();

                break;
        }
    }


    public void showMessage() {

        signViewModel.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressBar.setVisibility(View.GONE);
                if (s.contains("Welcome")) {
                    //login successfully
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else if (s.contains("failed")) {
                    //login failed
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
