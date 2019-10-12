package com.waleed.tripplanner.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

@Entity(tableName = "trips")
public class Trip {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String name;
    private String description;
    private String state;

    private String dirFrom;
    private double latFrom;
    private double LngFrom;

    private String dirTo;
    private double latTo;
    private double LngTo;
    private String date;
    private String time;

    private String type;

    public Trip() {
    }

    public Trip(int id, String name, String description, String state,
                String dirFrom, double latFrom, double lngFrom,
                String dirTo, double latTo, double lngTo,
                String date, String time, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.dirFrom = dirFrom;
        this.latFrom = latFrom;
        LngFrom = lngFrom;
        this.dirTo = dirTo;
        this.latTo = latTo;
        LngTo = lngTo;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDirFrom() {
        return dirFrom;
    }

    public void setDirFrom(String dirFrom) {
        this.dirFrom = dirFrom;
    }

    public double getLatFrom() {
        return latFrom;
    }

    public void setLatFrom(double latFrom) {
        this.latFrom = latFrom;
    }

    public double getLngFrom() {
        return LngFrom;
    }

    public void setLngFrom(double lngFrom) {
        LngFrom = lngFrom;
    }

    public String getDirTo() {
        return dirTo;
    }

    public void setDirTo(String dirTo) {
        this.dirTo = dirTo;
    }

    public double getLatTo() {
        return latTo;
    }

    public void setLatTo(double latTo) {
        this.latTo = latTo;
    }

    public double getLngTo() {
        return LngTo;
    }

    public void setLngTo(double lngTo) {
        LngTo = lngTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}