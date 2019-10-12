package com.waleed.tripplanner.view.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;
import com.waleed.tripplanner.R;
import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.repository.RoomDB;
import com.waleed.tripplanner.viewmodel.TripViewModel;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TripActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TripActivity";
    int FROM_REQUEST_CODE = 22;
    int TO_REQUEST_CODE = 44;

    // Set the fields to specify which types of place data to
    // return after the user has made a selection.
    List<Place.Field> fields;

    TripViewModel tripViewModel;
    ProgressBar progressBar;
    TextView textViewFrom, textViewTo, textViewDate, textViewTime;
    TextInputLayout textInputLayout_name, textInputLayout_description;
    RadioGroup radioGroupType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.new_trip));

        tripViewModel = ViewModelProviders.of(this).get(TripViewModel.class);


        if (!Places.isInitialized()) {
            // Places.initialize(TripActivity.this, getResources().getString(R.string.google_maps_key));
            Places.initialize(TripActivity.this, "AIzaSyBs3LkIHELdWxwUjpOLetp8WakMPNjMqAM");
        }


        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

//        // Initialize the AutocompleteSupportFragment.
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_from);
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(fields);
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
//            }
//        });

        init();
    }

    private void init() {

        textInputLayout_name = findViewById(R.id.textInputLayout_name);
        textInputLayout_description = findViewById(R.id.textInputLayout_description);

        textViewFrom = findViewById(R.id.textViewFrom);
        textViewTo = findViewById(R.id.textViewTo);

        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);

        radioGroupType = findViewById(R.id.radioGroupType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trip_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_menu_item:
                saveTrip();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTrip() {
        Trip trip =new Trip();
        tripViewModel.saveTrip(trip);

    }


    private void startPlacesIntent(int request) {
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(TripActivity.this);
        startActivityForResult(intent, request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FROM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());

                LatLng latLng = place.getLatLng();
                textViewFrom.setText("" + latLng.longitude);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.

                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i(TAG, "RESULT_CANCELED");

            }
        } else if (requestCode == TO_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());

                LatLng latLng = place.getLatLng();
                textViewTo.setText("" + latLng.longitude);


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.

                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i(TAG, "RESULT_CANCELED");

            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textViewFrom:
                startPlacesIntent(FROM_REQUEST_CODE);
                break;
            case R.id.textViewTo:
                startPlacesIntent(TO_REQUEST_CODE);
                break;
            case R.id.textViewDate:
                getDate();
                break;
            case R.id.textViewTime:
                getTime();
                break;
        }
    }

    public void getTime() {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textViewTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void getDate() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textViewDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


}
