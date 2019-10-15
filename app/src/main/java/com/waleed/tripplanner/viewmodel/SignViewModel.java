package com.waleed.tripplanner.viewmodel;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.waleed.tripplanner.repository.FireBaseRepo;
import com.waleed.tripplanner.view.activities.LoginActivity;
import com.waleed.tripplanner.view.activities.MainActivity;
import com.waleed.tripplanner.view.activities.RegisterActivity;

public class SignViewModel extends ViewModel {

    FireBaseRepo fireBaseRepo;
    LoginActivity loginActivity;
    RegisterActivity registerActivity;


    public SignViewModel(LoginActivity loginActivity) {
        fireBaseRepo = new FireBaseRepo(this);
        this.loginActivity = loginActivity;
    }

    public SignViewModel(RegisterActivity registerActivity) {
        fireBaseRepo = new FireBaseRepo(this);
        this.registerActivity = registerActivity;
    }

    public void login(String email, String password) {

        if (!email.isEmpty() && !email.equals(null)
                && !password.isEmpty() && !password.equals(null)) {

            removeEmptyDataError();

            fireBaseRepo.login(email, password);
        } else {
            emptyDataError();
        }

    }

    public void register(String email, String password, String rePassword) {

        if (!email.isEmpty() && !email.equals(null)
                && !password.isEmpty() && !password.equals(null)
                && !rePassword.isEmpty() && !rePassword.equals(null)) {

            removeEmptyDataError();

            fireBaseRepo.register(email, password);
        } else if (!password.equals(rePassword)) {
            passwordError();
        } else {
            emptyDataError();
        }

    }

    public void sendMessage() {

        Log.d("FireBaseRepo", "sendMessage");

        if (loginActivity != null) {
            Log.d("FireBaseRepo", "loginActivity successfully");

            loginActivity.showMessage("Login successfully");
            loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
            loginActivity.finish();

        } else if (registerActivity != null) {
            Log.d("FireBaseRepo", " registerActivitysuccessfully");
            registerActivity.showMessage("Register successfully");
            registerActivity.startActivity(new Intent(registerActivity, MainActivity.class));
            registerActivity.finish();
        }

    }

    public void sendError(String error) {

        if (loginActivity != null) {
            loginActivity.showMessage("Login Failed" + error);
        } else if (registerActivity != null) {
            registerActivity.showMessage("register Failed" + error);
        }

    }

    public void emptyDataError() {
        if (loginActivity != null) {
            loginActivity.emptyDataError();
        } else if (registerActivity != null) {
            registerActivity.emptyDataError();
        }
    }

    private void passwordError() {
        if (registerActivity != null) {
            registerActivity.passwordError();
        }
    }

    public void removeEmptyDataError() {
        if (loginActivity != null) {
            loginActivity.removeEmptyDataError();
        } else if (registerActivity != null) {
            registerActivity.removeEmptyDataError();
        }
    }


}
