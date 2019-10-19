package com.waleed.tripplanner.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.waleed.tripplanner.R;
import com.waleed.tripplanner.viewmodel.SignViewModel;


public class LogoutFragment extends Fragment {

    private SignViewModel signViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signOut();
    }

//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        signViewModel = ViewModelProviders.of(this, new SignViewModelFactory(this)).get(SignViewModel.class);
//
//
//        View root = inflater.inflate(R.layout.fragment_logout, container, false);
//
//        return root;
//    }

    public void signOut() {
        signViewModel.signOut();
    }

    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

    }


    class SignViewModelFactory implements ViewModelProvider.Factory {
        private LogoutFragment logoutFragment;


        public SignViewModelFactory(LogoutFragment logoutFragment) {
            this.logoutFragment = logoutFragment;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SignViewModel(logoutFragment);
        }
    }

}