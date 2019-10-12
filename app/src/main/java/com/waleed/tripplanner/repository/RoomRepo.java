package com.waleed.tripplanner.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.waleed.tripplanner.model.Trip;

import java.util.List;

public class RoomRepo {

    private static final String TAG = "RoomRepo";
    private Context context;

    public RoomRepo(Context context) {
        this.context = context;
    }

    private void getAllTrips() {

        class GetAllTrips extends AsyncTask<Void, Void, List<Trip>> {

            @Override
            protected List<Trip> doInBackground(Void... voids) {
                List<Trip> tripList = RoomDB.getRoomDB(context).getTripDAO().getAllTrips();
                return tripList;
            }

            @Override
            protected void onPostExecute(List<Trip> trips) {
                super.onPostExecute(trips);

            }
        }

        new GetAllTrips().execute();

    }

    private void getTrip(int id) {

        class GetTrip extends AsyncTask<Integer, Void, Trip> {

            @Override
            protected Trip doInBackground(Integer... integers) {
                Trip trip = RoomDB.getRoomDB(context).getTripDAO().getTrip(integers[0]);
                return trip;
            }

            @Override
            protected void onPostExecute(Trip trip) {
                super.onPostExecute(trip);
            }
        }

        new GetTrip().execute(id);

    }

    private void setTrip(Trip trip) {

        class SetTrip extends AsyncTask<Trip, Void, Void> {

            @Override
            protected Void doInBackground(Trip... trips) {
                RoomDB.getRoomDB(context).getTripDAO().insert(trips[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        new SetTrip().execute(trip);

    }

    private void updateTrip(Trip trip) {

        class UpdateTrip extends AsyncTask<Trip, Void, Void> {

            @Override
            protected Void doInBackground(Trip... trips) {
                RoomDB.getRoomDB(context).getTripDAO().update(trips[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        new UpdateTrip().execute(trip);

    }

    private void deleteTrip(Trip trip) {

        class DeleteTrip extends AsyncTask<Trip, Void, Void> {

            @Override
            protected Void doInBackground(Trip... trips) {
                RoomDB.getRoomDB(context).getTripDAO().delete(trips[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        new DeleteTrip().execute(trip);

    }

}
