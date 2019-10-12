package com.waleed.tripplanner.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.waleed.tripplanner.R;
import com.waleed.tripplanner.view.activities.TripActivity;
import com.waleed.tripplanner.viewmodel.UpcomingTripViewModel;


public class UpcomingTripFragment extends Fragment {

    private UpcomingTripViewModel upcomingTripViewModel;
    RecyclerView trip_recycler_view;
    FloatingActionButton add_fab;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        upcomingTripViewModel = ViewModelProviders.of(this).get(UpcomingTripViewModel.class);

        root = inflater.inflate(R.layout.fragment_upcoming_trip, container, false);




        upcomingTripViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);
            }
        });

        add_fab = root.findViewById(R.id.add_fab);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TripActivity.class));
            }
        });

        setupRecyclerView();
        return root;
    }


    private void setupRecyclerView() {
        trip_recycler_view = root.findViewById(R.id.trip_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        trip_recycler_view.setLayoutManager(layoutManager);


    }

}