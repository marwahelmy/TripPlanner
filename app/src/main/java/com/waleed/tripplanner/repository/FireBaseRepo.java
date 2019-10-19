package com.waleed.tripplanner.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.waleed.tripplanner.model.User;
import com.waleed.tripplanner.viewmodel.ProfileViewModel;
import com.waleed.tripplanner.viewmodel.SignViewModel;

public class FireBaseRepo {

    private FirebaseAuth mAuth;
    private static final String TAG = "FireBaseRepo";
    SignViewModel signViewModel;
    ProfileViewModel profileViewModel;

    public FireBaseRepo() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FireBaseRepo(SignViewModel signViewModel) {
        // Initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        this.signViewModel = signViewModel;
    }

    public FireBaseRepo(ProfileViewModel profileViewModel) {
        mAuth = FirebaseAuth.getInstance();
        this.profileViewModel = profileViewModel;
    }


    public void login(final String email, final String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //  Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            // updateUI(firebaseUser);

                            if (firebaseUser != null) {
                                // Name, email address, and profile photo Url
                                String name = firebaseUser.getDisplayName();
                                String email = firebaseUser.getEmail();
                                // Check if user's email is verified
                                //boolean emailVerified = firebaseUser.isEmailVerified();

                                // The user's ID, unique to the Firebase project. Do NOT use this value to
                                // authenticate with your backend server, if you have one. Use
                                // FirebaseUser.getIdToken() instead.
                                // String uid = firebaseUser.getUid();

                            }
                            Log.d(TAG, "signInWithEmail:success");
                            signViewModel.sendMessage();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //updateUI(null);

                            signViewModel.sendError(task.getException().getMessage());
                        }

                    }
                });

    }

    public void register(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            // updateUI(firebaseUser);

                            if (firebaseUser != null) {
                                // Name, email address, and profile photo Url
                                String name = firebaseUser.getDisplayName();
                                String email = firebaseUser.getEmail();

                                // Check if user's email is verified
                                // boolean emailVerified = firebaseUser.isEmailVerified();

                                // The user's ID, unique to the Firebase project. Do NOT use this value to
                                // authenticate with your backend server, if you have one. Use
                                // FirebaseUser.getIdToken() instead.
                                // String uid = firebaseUser.getUid();

                            }

                            signViewModel.sendMessage();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            // updateUI(null);
                            signViewModel.sendError(task.getException().getMessage());
                        }
                    }
                });
    }

    public void sendEmail(final String email) {

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            signViewModel.sendMessage();
                        } else {
                            signViewModel.sendError(task.getException().getMessage());
                        }
                    }
                });
    }

    public void updateUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                // .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

//        //----------------
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.updateEmail("user@example.com")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User email address updated.");
//                        }
//                    }
//                });
//
//        //-------------------
//
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String newPassword = "SOME-SECURE-PASSWORD";
//
//        user.updatePassword(newPassword)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User password updated.");
//                        }
//                    }
//                });
//
//        //----------------------
    }

    public User getUserData() {
        User user = null;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            String name = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();
            Uri photoUrl = firebaseUser.getPhotoUrl();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = firebaseUser.getUid();

            user = new User(uid, email, name, photoUrl);
        }

        return user;
    }


    public void signOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseAuth.getInstance().signOut();
            signViewModel.sendMessage();
        } else {

        }

    }


    public void deleteAccount() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
    }


}
