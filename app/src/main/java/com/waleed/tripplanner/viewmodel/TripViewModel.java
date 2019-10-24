package com.waleed.tripplanner.viewmodel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
        tripRoomRepo.setTrip(trip);
       // setAlarm(trip);
    }


    public void updateTrip(Trip trip) {
        tripRoomRepo.updateTrip(trip);
        //updateAlarm(trip);
    }


    public void showMessage(String message) {
        if (tripActivity != null) {
            tripActivity.showMessage(message);
        }

    }


    public void setAlarm(Trip trip) {


        Calendar cal = Calendar.getInstance();
        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        try {
            date = sdf.parse(trip.getDate() + " " + trip.getTime());
            cal.setTime(date);
            cal.set(Calendar.SECOND, 0);

            Log.d("setAlarm", "Calendar.YEAR= " + cal.get(Calendar.YEAR));
            Log.d("setAlarm", "Calendar.MONTH=" + cal.get(Calendar.MONTH));
            Log.d("setAlarm", "Calendar.DAY_OF_MONTH= " + cal.get(Calendar.DAY_OF_MONTH));
            Log.d("setAlarm", "Calendar.HOUR_OF_DAY= " + cal.get(Calendar.HOUR_OF_DAY));
            Log.d("setAlarm", "Calendar.MINUTE= " + cal.get(Calendar.MINUTE));


        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("setAlarm", "exception= " + e);
        }


        AlarmManager alarmManager = (AlarmManager) tripActivity.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(tripActivity, AlarmReceiver.class);
        alarmIntent.putExtra("amTrip", trip);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(tripActivity, trip.getId(), alarmIntent, PendingIntent.FLAG_ONE_SHOT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP ,alarmTime ,AlarmManager.INTERVAL_DAY ,pendingIntent);

    }


    public void updateAlarm(Trip trip) {

        Calendar cal = Calendar.getInstance();
        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        try {
            date = sdf.parse(trip.getDate() + " " + trip.getTime());
            cal.setTime(date);
            cal.set(Calendar.SECOND, 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AlarmManager alarmManager = (AlarmManager) tripActivity.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(tripActivity, AlarmReceiver.class);
        alarmIntent.putExtra("amTrip", trip);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(tripActivity, trip.getId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    public void cancelAlarm(Trip trip) {

        AlarmManager alarmManager = (AlarmManager) alarmActivity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(alarmActivity, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarmActivity, trip.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);

    }

}