package com.waleed.tripplanner.repository.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

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

    public void setTrip(final Trip trip) {

        class SetTrip extends AsyncTask<Trip, Void, Void> {

            @Override
            protected Void doInBackground(Trip... trips) {
                int i = getDB().insert(trip);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                tripViewModel.setAlarm(trip);
                //  tripViewModel.setAlarm(trip.getId());

                tripViewModel.showMessage("add Successfully");
            }
        }

        new SetTrip().execute(trip);

    }

    public void updateTrip(final Trip trip) {

        class UpdateTrip extends AsyncTask<Trip, Void, Void> {

            @Override
            protected Void doInBackground(Trip... trips) {
                getDB().update(trips[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                tripViewModel.updateAlarm(trip);
                tripViewModel.showMessage("update Successfully");
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


    public void getTrips(final MutableLiveData<List<Trip>> mutableLiveData, final String tripState) {

        class GetTrips extends AsyncTask<Void, Void, List<Trip>> {

            @Override
            protected List<Trip> doInBackground(Void... avoid) {
                List<Trip> tripList = getDB().getTrips(tripState);
                return tripList;
            }

            @Override
            protected void onPostExecute(List<Trip> trips) {
                super.onPostExecute(trips);
                mutableLiveData.setValue(trips);

            }
        }

        new GetTrips().execute();
    }


    public void getAllTrips(final MutableLiveData<List<Trip>> mutableLiveData) {

        class GetTrips extends AsyncTask<Void, Void, List<Trip>> {

            @Override
            protected List<Trip> doInBackground(Void... avoid) {
                List<Trip> tripList = getDB().getAllTrips();
                return tripList;
            }

            @Override
            protected void onPostExecute(List<Trip> trips) {
                super.onPostExecute(trips);
                //  allTripsViewModel.setLiveData(trips);
                mutableLiveData.setValue(trips);

            }
        }

        new GetTrips().execute();
    }

}
