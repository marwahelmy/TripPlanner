package com.waleed.tripplanner.repository.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.waleed.tripplanner.model.Trip;

@Database(entities = {Trip.class}, version = 1,exportSchema = false)
public abstract class TripDataBase extends RoomDatabase {

    private static TripDataBase INSTANCE;

    public static TripDataBase getDBInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, TripDataBase.class, "trip-database").build();

            // anther way
           // Builder<TripDataBase> builder = Room.databaseBuilder(context, TripDataBase.class, "trip-database");
           // INSTANCE = builder.build();

        }
        return INSTANCE;
    }


    public abstract TripDAO getTripDAO();

}