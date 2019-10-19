package com.waleed.tripplanner.viewmodel;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.waleed.tripplanner.repository.FireBaseRepo;
import com.waleed.tripplanner.utils.Validate;
import com.waleed.tripplanner.view.activities.ForgotPasswordActivity;
import com.waleed.tripplanner.view.activities.LoginActivity;
import com.waleed.tripplanner.view.activities.MainActivity;
import com.waleed.tripplanner.view.activities.RegisterActivity;
import com.waleed.tripplanner.view.activities.SplashActivity;
import com.waleed.tripplanner.view.fragments.LogoutFragment;

public class SignViewModel extends ViewModel {

    FireBaseRepo fireBaseRepo;
    LoginActivity loginActivity;
    RegisterActivity registerActivity;
    ForgotPasswordActivity passwordActivity;
    LogoutFragment logoutFragment;


    public SignViewModel(LoginActivity loginActivity) {
        fireBaseRepo = new FireBaseRepo(this);
        this.loginActivity = loginActivity;
    }

    public SignViewModel(RegisterActivity registerActivity) {
        fireBaseRepo = new FireBaseRepo(this);
        this.registerActivity = registerActivity;
    }

    public SignViewModel(ForgotPasswordActivity passwordActivity) {
        fireBaseRepo = new FireBaseRepo(this);
        this.passwordActivity = passwordActivity;
    }

    public SignViewModel(LogoutFragment logoutFragment) {
        fireBaseRepo = new FireBaseRepo(this);
        this.logoutFragment = logoutFragment;
    }


    public void login(String email, String password) {

        if (Validate.isNetworkAvailable(loginActivity)) {

            fireBaseRepo.login(email, password);
        } else {
            networkError();
        }

    }

    public void register(String email, String password) {

        if (Validate.isNetworkAvailable(registerActivity)) {

            fireBaseRepo.register(email, password);
        } else {
            networkError();
        }

    }

    public void sendEmail(String email) {

        if (Validate.isNetworkAvailable(passwordActivity)) {

            fireBaseRepo.sendEmail(email);
        } else {
            networkError();
        }

    }

    public void signOut() {
        fireBaseRepo.signOut();
    }

    public void sendMessage() {

        if (loginActivity != null) {

            loginActivity.showMessage("Login successfully");
            loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
            loginActivity.finish();

        } else if (registerActivity != null) {
            registerActivity.showMessage("Register successfully");
            registerActivity.startActivity(new Intent(registerActivity, MainActivity.class));
            registerActivity.finish();

        } else if (passwordActivity != null) {
            passwordActivity.showMessage();

        } else if (logoutFragment != null) {
            logoutFragment.startActivity(new Intent(logoutFragment.getActivity(), SplashActivity.class));
            logoutFragment.getActivity().finish();
        }

    }

    public void sendError(String error) {

        Log.d("sendError", "sendError: error = " + error);
        if (loginActivity != null) {
            loginActivity.showMessage("Login Failed , please try again");
        } else if (registerActivity != null) {
            registerActivity.showMessage("register Failed , please try again");
        } else if (passwordActivity != null) {
            passwordActivity.showError("Failed to send the Email , please try again");
        } else if (logoutFragment != null) {
            logoutFragment.showError("No user available");
        }

    }

    public void networkError() {

        if (loginActivity != null) {
            loginActivity.showNetworkError("Network Error, Check your Internet Connection");
        } else if (registerActivity != null) {
            registerActivity.showNetworkError("Network Error, Check your Internet Connection");
        } else if (passwordActivity != null) {
            passwordActivity.showError("Network Error, Check your Internet Connection");
        }

    }


}
