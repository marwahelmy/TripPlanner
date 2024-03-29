package com.waleed.tripplanner.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.waleed.tripplanner.R;
import com.waleed.tripplanner.viewmodel.SyncViewModel;


public class SyncFragment extends Fragment {

    private SyncViewModel syncViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        syncViewModel =
                ViewModelProviders.of(this).get(SyncViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sync, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        syncViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}