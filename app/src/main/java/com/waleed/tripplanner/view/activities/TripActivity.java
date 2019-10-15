package com.waleed.tripplanner.view.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;
import com.waleed.tripplanner.R;
import com.waleed.tripplanner.model.Trip;
import com.waleed.tripplanner.viewmodel.TripViewModel;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class TripActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TripActivity";

    TripViewModel tripViewModel;
    TextView textViewFrom, textViewTo, textViewDate, textViewTime;
    TextInputLayout textInputLayout_name, textInputLayout_description;
    RadioGroup radioGroupType;

    String location_from_name;
    double location_from_lat;
    double location_from_lng;

    String location_to_name;
    double location_to_lat;
    double location_to_lng;

    String state;
    String type;

    int FROM_REQUEST_CODE = 22;
    int TO_REQUEST_CODE = 44;

    // zero means new trip and 1 means update an old trip
    int mode = 0;
    private Trip oldTrip;

    // Set the fields to specify which types of place data to
    // return after the user has made a selection.
    List<Place.Field> fields;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.new_trip));

        tripViewModel = ViewModelProviders.of(this, new TripViewModelFactory(this)).get(TripViewModel.class);

        if (!Places.isInitialized()) {
            Places.initialize(TripActivity.this, getResources().getString(R.string.google_maps_key));
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

        if (getIntent().getExtras() != null) {
            mode = 1;
            oldTrip = getIntent().getExtras().getParcelable("currentTrip");
            setTrip(oldTrip);
        } else {
            mode = 0;
        }

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
                if (mode == 0) {
                    tripViewModel.saveTrip(getTrip());
                } else if (mode == 1) {

                    tripViewModel.updateTrip(getTrip());

                    if (oldTrip.equals(getTrip())) {

                        Log.d(TAG, "onOptionsItemSelected: oldTrip == getTrip()");
                        // tripViewModel.updateTrip(getTrip());
                    } else {
                        Log.d(TAG, "onOptionsItemSelected: else");

                    }
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FROM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + " , " +
                        place.getLatLng());

                location_from_name = place.getName();
                location_from_lat = place.getLatLng().latitude;
                location_from_lng = place.getLatLng().longitude;

                textViewFrom.setText(place.getName());

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

                location_to_name = place.getName();
                location_to_lat = place.getLatLng().latitude;
                location_to_lng = place.getLatLng().longitude;

                textViewTo.setText(place.getName());

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


    private void init() {

        textInputLayout_name = findViewById(R.id.textInputLayout_name);
        textInputLayout_description = findViewById(R.id.textInputLayout_description);

        textViewFrom = findViewById(R.id.textViewFrom);
        textViewFrom.setOnClickListener(this);

        textViewTo = findViewById(R.id.textViewTo);
        textViewTo.setOnClickListener(this);

        textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setOnClickListener(this);

        textViewTime = findViewById(R.id.textViewTime);
        textViewTime.setOnClickListener(this);

        radioGroupType = findViewById(R.id.radioGroupType);

    }

    private void setTrip(Trip trip) {


        if (trip.getName() != null && !trip.getName().isEmpty()) {
            textInputLayout_name.getEditText().setText(trip.getName());
        }

        if (trip.getDescription() != null && !trip.getDescription().isEmpty()) {
            textInputLayout_description.getEditText().setText(trip.getDescription());
        }

        if (trip.getDate() != null && !trip.getDate().isEmpty()) {
            textViewDate.setText(trip.getDate());
        }

        if (trip.getTime() != null && !trip.getTime().isEmpty()) {
            textViewTime.setText(trip.getTime());
        }

        if (trip.getLocFrom() != null && !trip.getLocFrom().isEmpty()) {
            textViewFrom.setText(trip.getLocFrom());
            location_from_name = trip.getLocFrom();
            location_from_lat = trip.getLatFrom();
            location_from_lng = trip.getLngFrom();

        }

        if (trip.getLocTo() != null && !trip.getLocTo().isEmpty()) {
            textViewTo.setText(trip.getLocTo());
            location_to_name = trip.getLocTo();
            location_to_lat = trip.getLatTo();
            location_to_lng = trip.getLngTo();
        }


        setTripState(trip.getState());

        setTripType(trip.getType());

    }

    private Trip getTrip() {

        Trip trip;

        if (mode == 0) {
            trip = new Trip();
        } else {
            trip = oldTrip;
        }


        trip.setName(textInputLayout_name.getEditText().getText().toString());
        trip.setDescription(textInputLayout_description.getEditText().getText().toString());

        trip.setDate(textViewDate.getText().toString());
        trip.setTime(textViewTime.getText().toString());

        trip.setState(getTripState());

        trip.setType(getTripType());

        trip.setLocFrom(location_from_name);
        trip.setLatFrom(location_from_lat);
        trip.setLngFrom(location_from_lng);

        trip.setLocTo(location_to_name);
        trip.setLatTo(location_to_lat);
        trip.setLngTo(location_to_lng);
        return trip;

    }

    private void startPlacesIntent(int request) {
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
        startActivityForResult(intent, request);
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

    String getTripState() {
        return state;
    }

    void setTripState(String state) {
        this.state = state;


    }

    void setTripType(String type) {


        if (type.equals(getResources().getString(R.string.one_direction))) {
            radioGroupType.check(R.id.radioOneDirection);

        } else if (type.equals(getResources().getString(R.string.round_trip))) {
            radioGroupType.check(R.id.radioRoundTrip);
        }
        this.type = type;
    }

    String getTripType() {

        switch (radioGroupType.getCheckedRadioButtonId()) {
            case R.id.radioOneDirection:
                type = getResources().getString(R.string.one_direction);
                break;
            case R.id.radioRoundTrip:
                type = getResources().getString(R.string.round_trip);
                break;
        }
        return type;
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    class TripViewModelFactory implements ViewModelProvider.Factory {
        private TripActivity tripActivity;


        public TripViewModelFactory(TripActivity tripActivity) {
            this.tripActivity = tripActivity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TripViewModel(tripActivity);
        }
    }

}
