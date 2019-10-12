package com.waleed.tripplanner.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.waleed.tripplanner.model.Trip;

public class TripViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TripViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void saveTrip(Trip trip) {
        if (validateData(trip)) {
            // go to  room repo
        } else {
            // show error
        }
    }

    public boolean validateData(Trip trip) {
        return true;
    }

    ;
}