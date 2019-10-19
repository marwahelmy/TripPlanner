package com.waleed.tripplanner.viewmodel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.repository.Room.TripRoomRepo;
import com.waleed.tripplanner.view.activities.AlarmActivity;
import com.waleed.tripplanner.view.activities.TripActivity;
import com.waleed.tripplanner.view.receiver.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TripViewModel extends ViewModel {

    TripActivity tripActivity;
    AlarmActivity alarmActivity;
    TripRoomRepo tripRoomRepo;

    public TripViewModel(TripActivity tripActivity) {
        this.tripActivity = tripActivity;
        tripRoomRepo = new TripRoomRepo(tripActivity, this);

    }

    public TripViewModel(AlarmActivity alarmActivity) {
        this.alarmActivity = alarmActivity;
        tripRoomRepo = new TripRoomRepo(tripActivity, this);

    }


    public void saveTrip(Trip trip) {
        if (validateData(trip)) {
            // go to  room repo
            tripRoomRepo.setTrip(trip);

            setAlarm(trip);
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
        if (tripActivity != null) {
            tripActivity.showMessage(message);
        }

    }


    public void setAlarm(Trip trip) {


        Calendar cal = Calendar.getInstance();
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            date = sdf.parse(trip.getDate() + " " + trip.getTime());
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        AlarmManager alarmManager = (AlarmManager) tripActivity.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(tripActivity, AlarmReceiver.class);
        alarmIntent.putExtra("amTrip",trip);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(tripActivity, 1, alarmIntent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP ,alarmTime ,AlarmManager.INTERVAL_DAY ,pendingIntent);

    }


    public void cancelAlarm() {

        AlarmManager alarmManager = (AlarmManager) alarmActivity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(alarmActivity, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarmActivity, 1, intent, 0);

        alarmManager.cancel(pendingIntent);

    }

}