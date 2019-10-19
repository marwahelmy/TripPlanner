package com.waleed.tripplanner.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.waleed.tripplanner.R;
import com.waleed.tripplanner.model.User;
import com.waleed.tripplanner.viewmodel.ProfileViewModel;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private View root;
    EditText editTextDisplayName, editTextEmail;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        init();

        if (profileViewModel.getUserData() != null) {

            setData(profileViewModel.getUserData());
        }
        return root;
    }

    private void init() {
        editTextDisplayName = root.findViewById(R.id.editTextDisplayName);
        editTextEmail = root.findViewById(R.id.editTextEmail);
    }

    private void setData(User user) {
        editTextDisplayName.setText(user.getUsername());
        editTextEmail.setText(user.getEmail());

    }
}