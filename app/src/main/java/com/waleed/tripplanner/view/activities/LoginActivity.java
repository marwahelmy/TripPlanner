package com.waleed.tripplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.waleed.tripplanner.R;
import com.waleed.tripplanner.viewmodel.SignViewModel;

import java.util.Locale;

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

        signViewModel = ViewModelProviders.of(this, new SignViewModelFactory(LoginActivity.this)).get(SignViewModel.class);

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
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.loginButton:

                startActivity(new Intent(LoginActivity.this, MainActivity.class));

//                progressBar.setVisibility(View.VISIBLE);
//                signViewModel.login(textInputLayout_email.getEditText().getText().toString(),
//                        textInputLayout_password.getEditText().getText().toString());

                break;
        }
    }


    public void showMessage(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void emptyDataError() {
        textInputLayout_email.setErrorEnabled(true);
        textInputLayout_email.setError("this field can not be Empty ");

        textInputLayout_password.setErrorEnabled(true);
        textInputLayout_password.setError("this field can not be Empty ");
    }

    public void removeEmptyDataError() {
        textInputLayout_email.setErrorEnabled(false);
        textInputLayout_email.setError("");

        textInputLayout_password.setErrorEnabled(false);
        textInputLayout_password.setError("");
    }


    class SignViewModelFactory implements ViewModelProvider.Factory {
        private LoginActivity loginActivity;


        public SignViewModelFactory(LoginActivity loginActivity) {
            this.loginActivity = loginActivity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SignViewModel(loginActivity);
        }
    }

}
