package com.waleed.tripplanner.viewmodel;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.repository.Room.TripRoomRepo;
import com.waleed.tripplanner.view.fragments.HistoryFragment;
import com.waleed.tripplanner.view.fragments.UpcomingTripFragment;
import com.waleed.tripplanner.view.receiver.AlarmReceiver;

import java.util.List;

public class AllTripsViewModel extends ViewModel {

    private MutableLiveData<List<Trip>> mutableLiveData;
    HistoryFragment historyFragment;
    TripRoomRepo tripRoomRepo;
    UpcomingTripFragment upcomingTripFragment;

    public AllTripsViewModel(HistoryFragment historyFragment) {
        mutableLiveData = new MutableLiveData<>();
        this.historyFragment = historyFragment;
        tripRoomRepo = new TripRoomRepo(historyFragment.getContext(), this);
    }


    public AllTripsViewModel(UpcomingTripFragment upcomingTripFragment) {
        mutableLiveData = new MutableLiveData<>();
        this.upcomingTripFragment = upcomingTripFragment;
        tripRoomRepo = new TripRoomRepo(upcomingTripFragment.getContext(), this);
    }


    public LiveData<List<Trip>> getMutableLiveData() {

        tripRoomRepo.getAllTrips(mutableLiveData);
        return mutableLiveData;
    }


    public LiveData<List<Trip>> getMutableLiveData(String state) {
        tripRoomRepo.getTrips(mutableLiveData, state);
        return mutableLiveData;
    }

    public void deleteTrip(Trip trip) {
        tripRoomRepo.deleteTrip(trip);
    }

    public void cancelAlarm(Trip trip , Activity activity) {

        AlarmManager alarmManager;

        alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, trip.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);

    }



}