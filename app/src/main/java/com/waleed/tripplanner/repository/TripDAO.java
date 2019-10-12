package com.waleed.tripplanner.repository;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.waleed.tripplanner.model.Trip;

import java.util.List;

@Dao
public interface TripDAO {

    @Insert
    void insert(Trip trip);

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trips")
    List<Trip> getAllTrips();


    @Query("SELECT * FROM trips WHERE id = :id")
    Trip getTrip(int id);

}
