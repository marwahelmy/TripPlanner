package com.waleed.tripplanner.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.waleed.tripplanner.view.activities.MapsActivity;
import com.waleed.tripplanner.view.adapter.HistoryAdapter;
import com.waleed.tripplanner.viewmodel.AllTripsViewModel;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    public HistoryFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }

    private AllTripsViewModel allTripsViewModel;
    private View root;
    private HistoryAdapter historyAdapter;
    FloatingActionButton map_fab;
    List<Trip> tripsList;

    @Override
    public void onStart() {
        super.onStart();

        updateRecyclerView();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        allTripsViewModel = ViewModelProviders.of(this, new TripsViewModelFactory(this)).get(AllTripsViewModel.class);

        root = inflater.inflate(R.layout.fragment_history, container, false);

        tripsList = new ArrayList<>();

        map_fab = root.findViewById(R.id.map_fab);
        map_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getActivity(), MapsActivity.class);
                mapIntent.putParcelableArrayListExtra("mapList", (ArrayList<? extends Parcelable>) tripsList);
                startActivity(mapIntent);
            }
        });
        setupRecyclerView();

        return root;
    }


    public void deleteTrip(Trip trip) {
        allTripsViewModel.deleteTrip(trip);
        allTripsViewModel.cancelAlarm(trip, getActivity());

    }

    private void setupRecyclerView() {

        RecyclerView trip_recycler_view = root.findViewById(R.id.trip_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        trip_recycler_view.setLayoutManager(layoutManager);
        historyAdapter = new HistoryAdapter(this);
        trip_recycler_view.setAdapter(historyAdapter);


    }

    public void updateRecyclerView() {

        allTripsViewModel.getMutableLiveData().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                tripsList = trips;

                historyAdapter.setTripList(trips);
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