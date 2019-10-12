package com.waleed.tripplanner.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.waleed.tripplanner.model.Trip;

@Database(entities = {Trip.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB INSTANCE;

    public static RoomDB getRoomDB(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, RoomDB.class, "trip-database").build();
        }
        return INSTANCE;
    }


    public abstract TripDAO getTripDAO();


    //   RoomDB.getRoomDB(getApplicationContext()).getTripDAO().getAllTrips();

}