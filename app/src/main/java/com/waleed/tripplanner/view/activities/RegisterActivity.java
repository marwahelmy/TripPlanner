package com.waleed.tripplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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
import com.waleed.tripplanner.utils.Validate;
import com.waleed.tripplanner.viewmodel.SignViewModel;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView back_to_login;
    Button registerButton;
    SignViewModel signViewModel;
    ProgressBar progressBar;
    TextInputLayout textInputLayout_email, textInputLayout_password, textInputLayout_re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signViewModel = ViewModelProviders.of(this, new SignViewModelFactory(RegisterActivity.this)).get(SignViewModel.class);

        init();
    }

    private void init() {

        progressBar = findViewById(R.id.progressBar);
        textInputLayout_email = findViewById(R.id.textInputLayout_email);
        textInputLayout_password = findViewById(R.id.textInputLayout_password);
        textInputLayout_re_password = findViewById(R.id.textInputLayout_re_password);

        back_to_login = findViewById(R.id.back_to_login);
        back_to_login.setOnClickListener(this);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_to_login:
                finish();
                break;

            case R.id.registerButton:


                if (Validate.isTextNotEmpty(textInputLayout_email.getEditText().getText().toString())) {

                    removerError();

                    if (Validate.isTextNotEmpty(textInputLayout_password.getEditText().getText().toString())) {

                        removerError();

                        if (Validate.isTextNotEmpty(textInputLayout_re_password.getEditText().getText().toString())) {
                            removerError();

                            if (Validate.arePasswordsEqual(textInputLayout_password.getEditText().getText().toString(),
                                    textInputLayout_re_password.getEditText().getText().toString())) {

                                removerError();

                                if (Validate.isValidEmail(textInputLayout_email.getEditText().getText().toString())) {

                                    removerError();
                                    progressBar.setVisibility(View.VISIBLE);
                                    registerButton.setVisibility(View.INVISIBLE);
                                    signViewModel.register(textInputLayout_email.getEditText().getText().toString(),
                                            textInputLayout_password.getEditText().getText().toString());

                                } else {
                                    textInputLayout_email.setErrorEnabled(true);
                                    textInputLayout_email.setError("Email is not Valid! ");
                                }

                            } else {
                                textInputLayout_password.setErrorEnabled(true);
                                textInputLayout_password.setError("Error Password");

                                textInputLayout_re_password.setErrorEnabled(true);
                                textInputLayout_re_password.setError("Error Password");
                            }

                        } else {
                            textInputLayout_re_password.setErrorEnabled(true);
                            textInputLayout_re_password.setError("this Field can not be Empty! ");
                        }

                    } else {
                        textInputLayout_password.setErrorEnabled(true);
                        textInputLayout_password.setError("Password can not be Empty! ");
                    }
                } else {
                    textInputLayout_email.setErrorEnabled(true);
                    textInputLayout_email.setError("Email can not be Empty! ");
                }


                break;
        }
    }

    private void removerError() {
        textInputLayout_email.setErrorEnabled(false);
        textInputLayout_email.setError("");

        textInputLayout_password.setErrorEnabled(false);
        textInputLayout_password.setError("");

        textInputLayout_re_password.setErrorEnabled(false);
        textInputLayout_re_password.setError("");
    }


    public void showMessage(String message) {
        progressBar.setVisibility(View.GONE);
        registerButton.setVisibility(View.VISIBLE);
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void showNetworkError(String netWorkError) {
        progressBar.setVisibility(View.GONE);
        registerButton.setVisibility(View.VISIBLE);
        Toast.makeText(this, netWorkError, Toast.LENGTH_SHORT).show();

    }

    class SignViewModelFactory implements ViewModelProvider.Factory {
        private RegisterActivity registerActivity;


        public SignViewModelFactory(RegisterActivity registerActivity) {
            this.registerActivity = registerActivity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SignViewModel(registerActivity);
        }
    }

}

