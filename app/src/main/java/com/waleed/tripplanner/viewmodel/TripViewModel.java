package com.waleed.tripplanner.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.repository.TripRoomRepo;
import com.waleed.tripplanner.view.activities.TripActivity;

public class TripViewModel extends ViewModel {

    TripActivity tripActivity;
    TripRoomRepo tripRoomRepo;

    public TripViewModel(TripActivity tripActivity) {
        this.tripActivity = tripActivity;
        tripRoomRepo = new TripRoomRepo(tripActivity, this);

    }


    public void saveTrip(Trip trip) {
        if (validateData(trip)) {
            // go to  room repo
            tripRoomRepo.setTrip(trip);
        } else {
            // show error
        }
    }


    public void updateTrip(Trip trip) {
        if (validateData(trip)) {
            // go to  room repo
            tripRoomRepo.updateTrip(trip);
        } else {
            // show error
        }
    }


    public boolean validateData(Trip trip) {
        return true;
    }

    public void showMessage(String message) {
        tripActivity.showMessage(message);
    }

}