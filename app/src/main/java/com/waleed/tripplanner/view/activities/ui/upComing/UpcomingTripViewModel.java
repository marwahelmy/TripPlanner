package com.waleed.tripplanner.view.activities.ui.upComing;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpcomingTripViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UpcomingTripViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}