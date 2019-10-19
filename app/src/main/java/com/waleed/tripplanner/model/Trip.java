package com.waleed.tripplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trips")
public class Trip implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String name;
    private String description;
    private String state;

    private String locFrom;
    private double latFrom;
    private double LngFrom;

    private String locTo;
    private double latTo;
    private double LngTo;

    private String date;
    private String time;

    private String dateBack;
    private String timeBack;

    private String type;

    public Trip() {
    }

    public Trip(int id, String name, String description, String state, String locFrom, double latFrom,
                double lngFrom, String locTo, double latTo, double lngTo, String date, String time, String dateBack,
                String timeBack, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.locFrom = locFrom;
        this.latFrom = latFrom;
        LngFrom = lngFrom;
        this.locTo = locTo;
        this.latTo = latTo;
        LngTo = lngTo;
        this.date = date;
        this.time = time;
        this.dateBack = dateBack;
        this.timeBack = timeBack;
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

    public String getLocFrom() {
        return locFrom;
    }

    public void setLocFrom(String locFrom) {
        this.locFrom = locFrom;
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

    public String getLocTo() {
        return locTo;
    }

    public void setLocTo(String locTo) {
        this.locTo = locTo;
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

    public String getDateBack() {
        return dateBack;
    }

    public void setDateBack(String dateBack) {
        this.dateBack = dateBack;
    }

    public String getTimeBack() {
        return timeBack;
    }

    public void setTimeBack(String timeBack) {
        this.timeBack = timeBack;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    protected Trip(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        state = in.readString();
        locFrom = in.readString();
        latFrom = in.readDouble();
        LngFrom = in.readDouble();
        locTo = in.readString();
        latTo = in.readDouble();
        LngTo = in.readDouble();
        date = in.readString();
        time = in.readString();
        dateBack = in.readString();
        timeBack = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(state);
        dest.writeString(locFrom);
        dest.writeDouble(latFrom);
        dest.writeDouble(LngFrom);
        dest.writeString(locTo);
        dest.writeDouble(latTo);
        dest.writeDouble(LngTo);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(dateBack);
        dest.writeString(timeBack);
        dest.writeString(type);
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}

