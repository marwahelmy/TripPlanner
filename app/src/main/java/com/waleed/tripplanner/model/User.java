package com.waleed.tripplanner.model;

import android.net.Uri;

public class User {
    private String email;
    private String username;
    private String userId;
    private Uri photoUrl;


    public User() {
    }

    public User(String userId, String email, String username, Uri photoUrl) {
        this.email = email;
        this.username = username;
        this.userId = userId;
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }
}
