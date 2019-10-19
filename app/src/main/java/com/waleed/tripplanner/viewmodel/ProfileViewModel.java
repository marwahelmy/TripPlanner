package com.waleed.tripplanner.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.waleed.tripplanner.model.User;
import com.waleed.tripplanner.repository.FireBaseRepo;

public class ProfileViewModel extends ViewModel {

    FireBaseRepo fireBaseRepo;

    public ProfileViewModel() {
        fireBaseRepo = new FireBaseRepo(this);

    }

    public User getUserData() {
        return fireBaseRepo.getUserData();
    }

}