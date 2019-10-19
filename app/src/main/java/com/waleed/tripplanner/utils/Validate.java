package com.waleed.tripplanner.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public static boolean isTextNotEmpty(String txt) {

//        if (txt != null && !txt.isEmpty()) {
//            return true;
//        } else {
//            return false;
//        }

        if (txt.matches("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidEmail(String email) {

//        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            return true;
//        } else {
//            return false;
//        }

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static boolean arePasswordsEqual(String pass1, String pass2) {
        if (pass1.equals(pass2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
