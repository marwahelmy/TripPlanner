package com.waleed.tripplanner.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.viewmodel.AllTripsViewModel;
import com.waleed.tripplanner.viewmodel.TripViewModel;

import java.util.List;

public class TripRoomRepo {

    private static final String TAG = "TripRoomRepo";

    private Context context;
    private TripViewModel tripViewModel;
    AllTripsViewModel allTripsViewModel;

    public TripRoomRepo(Context context, TripViewModel tripViewModel) {
        this.context = context;
        this.tripViewModel = tripViewModel;
    }

    public TripRoomRepo(Context context, AllTripsViewModel allTripsViewModel) {
        this.context = context;
        this.allTripsViewModel = allTripsViewModel;
    }


    public TripDAO getDB() {
        return TripDataBase.getDBInstance(context).getTripDAO();
    }

    public void getTrip(int id) {

        class GetTrip extends AsyncTask<Integer, Void, Trip> {

            @Override
            protected Trip doInBackground(Integer... integers) {
                Trip trip = getDB().getTrip(integers[0]);
                return trip;
            }

            @Override
            protected void onPostExecute(Trip trip) {
                super.onPostExecute(trip);
            }
        }

        new GetTrip().execute(id);

    }

    public void setTrip(Trip trip) {

        class SetTrip extends AsyncTask<Trip, Void, Void> {

            @Override
            protected Void doInBackground(Trip... trips) {
                getDB().insert(trips[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                tripViewModel.showMessage("add");
            }
        }

        new SetTrip().execute(trip);

    }

    public void updateTrip(Trip trip) {

        class UpdateTrip extends AsyncTask<Trip, Void, Void> {

            @Override
            protected Void doInBackground(Trip... trips) {
                getDB().update(trips[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                tripViewModel.showMessage("update");
            }
        }

        new UpdateTrip().execute(trip);

    }

    public void deleteTrip(Trip trip) {

        class DeleteTrip extends AsyncTask<Trip, Void, Void> {

            @Override
            protected Void doInBackground(Trip... trips) {
                getDB().delete(trips[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        new DeleteTrip().execute(trip);

    }

    public void getTrips(String tripState) {

        class GetTrips extends AsyncTask<String, Void, List<Trip>> {

            @Override
            protected List<Trip> doInBackground(String... strings) {
                List<Trip> tripList = getDB().getTrips(strings[0]);
                return tripList;
            }

            @Override
            protected void onPostExecute(List<Trip> trips) {
                super.onPostExecute(trips);

                Log.d(TAG, "onPostExecute: " + trips.get(0).getState());
                allTripsViewModel.setLiveData(trips);

            }
        }

        new GetTrips().execute(tripState);
    }


    public void getAllTrips() {

        class GetTrips extends AsyncTask<Void, Void, List<Trip>> {

            @Override
            protected List<Trip> doInBackground(Void... avoid) {
                List<Trip> tripList = getDB().getAllTrips();
                return tripList;
            }

            @Override
            protected void onPostExecute(List<Trip> trips) {
                super.onPostExecute(trips);
                allTripsViewModel.setLiveData(trips);

            }
        }

        new GetTrips().execute();
    }

}
