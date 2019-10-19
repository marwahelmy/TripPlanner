package com.waleed.tripplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.waleed.tripplanner.R;
import com.waleed.tripplanner.utils.Validate;
import com.waleed.tripplanner.viewmodel.SignViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button sendEmailButton;
    TextInputLayout textInputLayout_email;
    ProgressBar progressBar;
    SignViewModel signViewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();

        signViewModel = ViewModelProviders.of(this,
                new ForgotPasswordActivity.SignViewModelFactory(this)).get(SignViewModel.class);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validate.isTextNotEmpty(textInputLayout_email.getEditText().getText().toString())) {

                    textInputLayout_email.setErrorEnabled(false);
                    textInputLayout_email.setError(null);

                    if (Validate.isValidEmail(textInputLayout_email.getEditText().getText().toString())) {

                        textInputLayout_email.setErrorEnabled(false);
                        textInputLayout_email.setError(null);

                        sendEmailButton.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);

                        // send Email
                        signViewModel.sendEmail(textInputLayout_email.getEditText().getText().toString());

                    } else {
                        textInputLayout_email.setErrorEnabled(true);
                        textInputLayout_email.setError("Email is not Valid! ");
                    }

                } else {
                    textInputLayout_email.setErrorEnabled(true);
                    textInputLayout_email.setError("Email can not be Empty! ");
                }
            }
        });

    }

    private void init() {
        textInputLayout_email = findViewById(R.id.textInputLayout_email);
        progressBar = findViewById(R.id.progressBar);
        sendEmailButton = findViewById(R.id.sendEmailButton);
    }

    public void showError(String error) {
        progressBar.setVisibility(View.GONE);
        sendEmailButton.setVisibility(View.VISIBLE);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void showMessage() {
        Toast.makeText(this, "Email is sent, Check Your inbox", Toast.LENGTH_SHORT).show();
        //dialog
        finish();

    }

    class SignViewModelFactory implements ViewModelProvider.Factory {
        private ForgotPasswordActivity forgotPasswordActivity;


        public SignViewModelFactory(ForgotPasswordActivity forgotPasswordActivity) {
            this.forgotPasswordActivity = forgotPasswordActivity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SignViewModel(forgotPasswordActivity);
        }
    }

}
