package com.waleed.tripplanner.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.waleed.tripplanner.utils.NotificationHelper;
import com.waleed.tripplanner.R;
import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.viewmodel.TripViewModel;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewName, textViewFrom, textViewTo, textViewDescription;
    Button buttonStart, buttonLater, buttonCancel;
    TripViewModel tripViewModel;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        tripViewModel = ViewModelProviders.of(this, new TripViewModelFactory(this)).get(TripViewModel.class);


        init();

        if (getIntent().getExtras() != null) {
            trip = getIntent().getExtras().getParcelable("alarmTrip");
            setTripData();
        }
    }

    private void init() {
        textViewName = findViewById(R.id.textViewName);
        textViewFrom = findViewById(R.id.textViewFrom);
        textViewTo = findViewById(R.id.textViewTo);
        textViewDescription = findViewById(R.id.textViewDescription);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

        buttonLater = findViewById(R.id.buttonLater);
        buttonLater.setOnClickListener(this);

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);
    }

    public void setTripData() {
        textViewName.setText(trip.getName());
        textViewFrom.setText(trip.getLocFrom());
        textViewTo.setText(trip.getLocTo());
        textViewDescription.setText(trip.getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:
                //1- update trip state to done
                updateTripState(getResources().getString(R.string.trip_state_done));

                //2-open map with directions
                openMap(trip.getLatFrom(), trip.getLngFrom(), trip.getLatTo(), trip.getLngTo());

                //3- finish
                finish();
                break;
            case R.id.buttonLater:
                // open notification
                openNotification();
                break;
            case R.id.buttonCancel:
                //1- update trip state to canceled
                updateTripState(getResources().getString(R.string.trip_state_cancel));
                //2- cancel alarm
               // tripViewModel.cancelAlarm();

                //3- finish
                finish();
                break;
        }

    }


    public void openMap(double fromLat, double fromLng, double toLat, double toLng) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + fromLat + "," + fromLng + "&daddr =" + toLat + "," + toLng));
        startActivity(intent);

//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?daddr=" + toLat + "," + toLng));
//        startActivity(intent);
    }

    public void updateTripState(String state) {
        trip.setState(state);

        // confelifce replace
        tripViewModel.updateTrip(trip);

    }

    public void openNotification() {
        NotificationHelper notificationHelper = new NotificationHelper(this, trip);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }


    class TripViewModelFactory implements ViewModelProvider.Factory {
        private AlarmActivity alarmActivity;


        public TripViewModelFactory(AlarmActivity alarmActivity) {
            this.alarmActivity = alarmActivity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TripViewModel(alarmActivity);
        }
    }

}
