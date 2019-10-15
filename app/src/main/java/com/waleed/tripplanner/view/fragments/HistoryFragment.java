package com.waleed.tripplanner.view.fragments;

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

import com.waleed.tripplanner.R;
import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.view.adapter.HistoryAdapter;
import com.waleed.tripplanner.viewmodel.AllTripsViewModel;

import java.util.List;


public class HistoryFragment extends Fragment {

    public HistoryFragment() {
    }

    private AllTripsViewModel allTripsViewModel;
    private View root;
    private HistoryAdapter  historyAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        allTripsViewModel = ViewModelProviders.of(this, new TripsViewModelFactory(this)).get(AllTripsViewModel.class);

        root = inflater.inflate(R.layout.fragment_history, container, false);

        setupRecyclerView();

        updateRecyclerView();

        getAllTrips();



        return root;
    }

    private void getAllTrips() {
        allTripsViewModel.getAllTrip();
    }


    public void deleteTrip(Trip trip) {
        allTripsViewModel.deleteTrip(trip);
    }

    private void setupRecyclerView() {

        RecyclerView trip_recycler_view = root.findViewById(R.id.trip_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        trip_recycler_view.setLayoutManager(layoutManager);
        historyAdapter = new HistoryAdapter(this);
        trip_recycler_view.setAdapter(historyAdapter);


    }

    void updateRecyclerView() {

        allTripsViewModel.getMutableLiveData().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                historyAdapter.setTripList( trips);
            }
        });
    }


    class TripsViewModelFactory implements ViewModelProvider.Factory {
        private HistoryFragment historyFragment;


        public TripsViewModelFactory(HistoryFragment historyFragment) {
            this.historyFragment = historyFragment;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AllTripsViewModel(historyFragment);
        }
    }


}