package com.waleed.tripplanner.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.waleed.tripplanner.model.User;
import com.waleed.tripplanner.repository.FireBaseRepo;

public class SignViewModel extends ViewModel {

    FireBaseRepo fireBaseRepo;
    MediatorLiveData<String> liveData;
    User user;


    boolean state;

    public SignViewModel() {
        fireBaseRepo = new FireBaseRepo();
        liveData = new MediatorLiveData<>();
    }

    public LiveData<String> getLiveData() {
        if (isState()) {
            liveData.postValue("Welcome " + user.getUsername());
        } else {
            liveData.postValue("Login failed, please try again");
        }
        return liveData;
    }

    public void login(String email, String password) {
        setState(fireBaseRepo.login(email, password));
        Log.d("FireBaseRepo", "SignViewModel:state =" + state);


    }

    public void register(String email, String password) {
        state = fireBaseRepo.register(email, password);
    }


    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        getLiveData();
    }

}
