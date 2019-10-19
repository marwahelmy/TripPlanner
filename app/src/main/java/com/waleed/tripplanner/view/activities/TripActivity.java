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
import android.widget.LinearLayout;
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
import com.waleed.tripplanner.utils.Validate;
import com.waleed.tripplanner.viewmodel.TripViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class TripActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TripActivity";

    TripViewModel tripViewModel;
    TextView textViewFrom, textViewTo, textViewDate, textViewTime, textViewDateBack, textViewTimeBack;
    TextInputLayout textInputLayout_name, textInputLayout_description;
    RadioGroup radioGroupType;

    LinearLayout dateBackTimeLayout;

    String location_from_name;
    double location_from_lat;
    double location_from_lng;

    String location_to_name;
    double location_to_lat;
    double location_to_lng;

    String state;
    String tripType = "";

    int FROM_REQUEST_CODE = 22;
    int TO_REQUEST_CODE = 44;

    // zero means new trip and 1 means update an old trip
    int mode = 0;
    private Trip oldTrip, newTrip;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // Set the fields to specify which types of place data to
    // return after the user has made a selection.
    List<Place.Field> fields;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        if (getIntent().getExtras() != null) {
            mode = 1;
            getSupportActionBar().setTitle(getResources().getString(R.string.edit_trip));
            oldTrip = getIntent().getExtras().getParcelable("currentTrip");
            setTrip(oldTrip);
        } else {
            mode = 0;
            getSupportActionBar().setTitle(getResources().getString(R.string.new_trip));
        }

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


    }

    private void init() {

        dateBackTimeLayout = findViewById(R.id.dateBackTimeLayout);
        textViewDateBack = findViewById(R.id.textViewDateBack);
        textViewDateBack.setOnClickListener(this);
        textViewTimeBack = findViewById(R.id.textViewTimeBack);
        textViewTimeBack.setOnClickListener(this);


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
        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioOneDirection:
                        tripType = getResources().getString(R.string.one_direction);
                        dateBackTimeLayout.setVisibility(View.GONE);
                        break;
                    case R.id.radioRoundTrip:
                        tripType = getResources().getString(R.string.round_trip);
                        dateBackTimeLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


    }


    private void startPlacesIntent(int request) {
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
        startActivityForResult(intent, request);
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
                if (getTrip() != null) {
                    if (mode == 0) {
                        tripViewModel.saveTrip(getTrip());

                    } else if (mode == 1) {

                        if (oldTrip.getState().equals(getResources().getString(R.string.trip_state_up_coming))) {

                            getTrip().setId(oldTrip.getId());
                            tripViewModel.updateTrip(getTrip());

                        } else if (oldTrip.getState().equals(getResources().getString(R.string.trip_state_cancel))
                                || oldTrip.getState().equals(getResources().getString(R.string.trip_state_done))) {

                            tripViewModel.saveTrip(getTrip());
                        }
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
                getDate(textViewDate);
                break;
            case R.id.textViewTime:
                getTime(textViewTime);
                break;
            case R.id.textViewDateBack:
                getDate(textViewDateBack);
                break;
            case R.id.textViewTimeBack:
                getTime(textViewTimeBack);
                break;
        }
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

        //if type == round trip
        if (trip.getType().equals(getResources().getString(R.string.round_trip))) {
            dateBackTimeLayout.setVisibility(View.VISIBLE);
            textViewDateBack.setText(trip.getDateBack());
            textViewTimeBack.setText(trip.getTimeBack());

        }

    }

    private Trip getTrip() {


        if (mode == 0) {
            newTrip = new Trip();
            newTrip.setState(getResources().getString(R.string.trip_state_up_coming));
        } else {
            newTrip = oldTrip;
            newTrip.setState(oldTrip.getState());
        }

        if (isTripName() && isTripDescription()
                // && isFrom() && isTo()
                && isDate() && isTime()
                && isType()) {

            if (newTrip.getType().equals(getResources().getString(R.string.round_trip))) {
                if (isDateBack() && isTimeBack()) {
                    return newTrip;
                }

            } else {
                return newTrip;
            }


        }


        return null;

    }


    private void setTripState(String state) {
        this.state = state;
    }


    // round trip or one direction
    private void setTripType(String type) {

        if (type.equals(getResources().getString(R.string.one_direction))) {
            radioGroupType.check(R.id.radioOneDirection);

        } else if (type.equals(getResources().getString(R.string.round_trip))) {
            radioGroupType.check(R.id.radioRoundTrip);
        }
        this.tripType = type;
    }

    String getTripType() {
        return tripType;
    }

    private void getTime(final TextView textView) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textView.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void getDate(final TextView textView) {

        final Calendar c = Calendar.getInstance();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        textView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        textView.setText(calendar.getTime() + "");
                        Log.d(TAG, "onDateSet: date=" + calendar.getTime());
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //---------------------- validation methods
    public boolean isTripName() {
        // name
        if (Validate.isTextNotEmpty(textInputLayout_name.getEditText().getText().toString())) {
            newTrip.setName(textInputLayout_name.getEditText().getText().toString());

            textInputLayout_name.setErrorEnabled(false);
            textInputLayout_name.setError("");
            return true;

        } else {
            textInputLayout_name.setErrorEnabled(true);
            textInputLayout_name.setError("Name can't be empty ! ");
        }
        return false;
    }

    public boolean isTripDescription() {

        // description
        if (Validate.isTextNotEmpty(textInputLayout_description.getEditText().getText().toString())) {

            newTrip.setDescription(textInputLayout_description.getEditText().getText().toString());

            textInputLayout_description.setErrorEnabled(false);
            textInputLayout_description.setError("");
            return true;

        } else {
            textInputLayout_description.setErrorEnabled(true);
            textInputLayout_description.setError("description can't be empty ! ");
            return false;
        }

    }

    private boolean isFrom() {
        // from(start)
        if (Validate.isTextNotEmpty(textViewFrom.getText().toString())) {
            newTrip.setLocFrom(location_from_name);
            newTrip.setLatFrom(location_from_lat);
            newTrip.setLngFrom(location_from_lng);
            textViewFrom.setError(null);
            return true;

        } else {
            textViewFrom.setError("please select your start point");
            return false;
        }
    }

    private boolean isTo() {
        //to(destination)
        if (Validate.isTextNotEmpty(textViewTo.getText().toString())) {
            newTrip.setLocTo(location_to_name);
            newTrip.setLatTo(location_to_lat);
            newTrip.setLngTo(location_to_lng);
            textViewTo.setError(null);
            return true;
        } else {
            textViewTo.setError("please select your destination");
            return false;
        }
    }

    private boolean isType() {
        // type
        if (Validate.isTextNotEmpty(getTripType())) {
            newTrip.setType(getTripType());
            return true;
        } else {
            Toast.makeText(this, "please choose the type of your trip ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isTime() {
        // time
        if (Validate.isTextNotEmpty(textViewTime.getText().toString())) {
            newTrip.setTime(textViewTime.getText().toString());
            textViewTime.setError(null);
            return true;
        } else {
            textViewTime.setError("please select the trip time");
            return false;
        }
    }

    private boolean isDate() {
        // date
        if (Validate.isTextNotEmpty(textViewDate.getText().toString())) {

            try {
                Log.d(TAG, "isDate:textViewDate =  " + dateFormat.parse(textViewDate.getText().toString()));
                Log.d(TAG, "isDate:today's date =  " + Calendar.getInstance().getTime());

                if (dateFormat.parse(textViewDate.getText().toString()).before(Calendar.getInstance().getTime())) {


                    textViewDate.setError("please select a valid date");
                    return false;

                } else {
                    newTrip.setDate(textViewDate.getText().toString());
                    textViewDate.setError(null);
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                textViewDate.setError("please select a valid date");
                return false;
            }

        } else {
            textViewDate.setError("please select the trip date");
            return false;
        }

    }

    private boolean isTimeBack() {
        if (Validate.isTextNotEmpty(textViewTimeBack.getText().toString())) {
            newTrip.setTimeBack(textViewTimeBack.getText().toString());
            textViewTimeBack.setError(null);
            return true;
        } else {
            textViewTimeBack.setError("please select back time");
            return false;
        }
    }

    private boolean isDateBack() {

        // date
        if (Validate.isTextNotEmpty(textViewDateBack.getText().toString())) {

            try {
                if (dateFormat.parse(textViewDateBack.getText().toString()).getTime() >= System.currentTimeMillis()) {
                    newTrip.setDate(textViewDateBack.getText().toString());
                    textViewDateBack.setError(null);
                    return true;

                } else {
                    textViewDateBack.setError("please select a valid date");
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                textViewDateBack.setError("please select a valid date");

                return false;
            }

        } else {
            textViewDateBack.setError("please select the trip date");
            return false;
        }

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
