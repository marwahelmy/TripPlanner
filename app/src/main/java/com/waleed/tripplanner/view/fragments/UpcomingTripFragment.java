package com.waleed.tripplanner.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.waleed.tripplanner.R;
import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.view.activities.TripActivity;
import com.waleed.tripplanner.view.adapter.UpComingTripsAdapter;
import com.waleed.tripplanner.viewmodel.AllTripsViewModel;

import java.util.List;


public class UpcomingTripFragment extends Fragment {

    FloatingActionButton add_fab;
    View root;

    private AllTripsViewModel allTripsViewModel;
    private UpComingTripsAdapter upComingTripsAdapter;

    @Override
    public void onStart() {
        super.onStart();
        updateRecyclerView();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        allTripsViewModel = ViewModelProviders.of(this, new TripsViewModelFactory(this)).get(AllTripsViewModel.class);

        root = inflater.inflate(R.layout.fragment_upcoming_trip, container, false);

        add_fab = root.findViewById(R.id.add_fab);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TripActivity.class));
            }
        });

        setupRecyclerView();

        getAllTrips();

        return root;
    }

    private void getAllTrips() {
        allTripsViewModel.getTrips(getResources().getString(R.string.trip_state_up_coming));
    }


    public void deleteTrip(Trip trip) {
        allTripsViewModel.deleteTrip(trip);
    }

    private void setupRecyclerView() {

        RecyclerView trip_recycler_view = root.findViewById(R.id.trip_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        trip_recycler_view.setLayoutManager(layoutManager);
        upComingTripsAdapter = new UpComingTripsAdapter(this);
        trip_recycler_view.setAdapter(upComingTripsAdapter);


    }

    void updateRecyclerView() {

        allTripsViewModel.getMutableLiveData().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                upComingTripsAdapter.setTripList(trips);
            }
        });
    }


    class TripsViewModelFactory implements ViewModelProvider.Factory {
        private UpcomingTripFragment UpcomingTripFragment;


        public TripsViewModelFactory(UpcomingTripFragment UpcomingTripFragment) {
            this.UpcomingTripFragment = UpcomingTripFragment;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AllTripsViewModel(UpcomingTripFragment);
        }
    }


}